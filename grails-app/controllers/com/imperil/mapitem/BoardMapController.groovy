package com.imperil.mapitem

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

import javax.servlet.http.HttpServletRequest

import org.codehaus.groovy.grails.io.support.IOUtils
import org.grails.plugins.csv.CSVWriter
import org.springframework.transaction.TransactionStatus
import org.springframework.web.multipart.MultipartFile

import com.dynamix.user.AppUser
import com.imperil.setup.TerritoryPropertyHelper



class BoardMapController {

  SpringSecurityService  springSecurityService

  static allowedMethods = [
    delete:['DELETE']]


  def index() {
  }

  def show() {
    def paramId = params.id
    BoardMap result = BoardMap.get(paramId)
    JSON.use('VERY_DETAILED') { render result as JSON }
  }

  def updateGeoLocations() {
    log.debug("\$request.JSON: $request.JSON")
    String territoryIdStr = request.JSON.id
    List locations = request.JSON.locations

    Long territoryId = Long.valueOf(territoryIdStr)
    Territory territory = Territory.get(territoryId)

    BoardMap.withTransaction { TransactionStatus status ->
      try {
        GeoLocation.executeUpdate("delete GeoLocation g where g.territory = ?", [territory])
        territory.geoLocations = locations.collect {
          GeoLocation geoLoc = new GeoLocation(latitude:it.latitude, longitude:it.longitude, territory:territory).save(failOnError:true)
        }
      } catch (Exception e) {
        log.error(e.getMessage())
        status.setRollbackOnly();
        response.sendError(500, (e != null?e.getMessage():"null exception"))
        return
      }
    }
    JSON.use('VERY_DETAILED') { render territory as JSON }
  }

  def create() {
    def currentUser = springSecurityService.currentUser
    log.trace("BoardMap.create called for user ${currentUser}")

    BoardMap boardMap = request.JSON.boardMap

    if (BoardMap.findByName(boardMap.name) != null) {
      render(status: 403, text: "${newBoardMap.name} already exists")
      return
    }
    final BoardMap result
    String oldBoardMapIdString = request.JSON.oldBoardMapId
    if (oldBoardMapIdString) {
      Long oldBoardMapId = Long.valueOf(oldBoardMapIdString)
      BoardMap oldBoardMap = BoardMap.get(oldBoardMapId)
      result = cloneBoardMap(boardMap.name, boardMap.description, oldBoardMap, currentUser)
    } else {
      result = new BoardMap(name:boardMap.name, description:boardMap.description, createdBy:currentUser).save(failOnError:true)
    }

    JSON.use('SIMPLE') { render result as JSON }
  }

  def delete() {
    def currentUser = springSecurityService.currentUser
    log.trace("BoardMap.delete called for user ${currentUser}")

    try {
      Long id = params.id as Long

      BoardMap boardMap = BoardMap.get(id)

      boardMap.delete()
      render(status: 200, text: "${boardMap.name} deleted")
    } catch (Exception e) {
      render(status: 403, text: "error deleting boardmap: $params.id: "+e.getMessage())
    }
  }


  def listMine() {
    def currentUser = springSecurityService.currentUser
    log.trace("listMine called for user ${currentUser}")

    def result = BoardMap.findAllByCreatedBy(currentUser)

    JSON.use('SIMPLE') { render result as JSON }
  }

  def list() {
    def result = BoardMap.findAll()

    JSON.use('SIMPLE') { render result as JSON }
  }

  public static BoardMap cloneBoardMap(String name, String description, BoardMap boardMap, AppUser currentUser) {
    BoardMap result = null

    BoardMap.withTransaction { TransactionStatus status ->
      try {
        result = new BoardMap(name:name, description:description, createdBy:currentUser);
        result.save(failOnError:true)

        Map<String, Territory> territoryMap = [:]

        boardMap.continents.each { Continent continent->
          Continent newContinent = new Continent(name:continent.name, description:continent.description, boardMap:result).save(failOnError:true);
          newContinent.territories = []
          continent.territories.each { Territory territory ->
            Territory newTerritory = new Territory(name:territory.name, description:territory.description, continent:newContinent).save(failOnError:true)
            territoryMap.put(newTerritory.name, newTerritory)

            newTerritory.geoLocations = []
            territory.geoLocations?.each { GeoLocation geoLocation ->
              GeoLocation newGeoLocation = new GeoLocation(latitude:geoLocation.latitude, longitude:geoLocation.longitude, territory:newTerritory).save(failOnError:true)
            }
          }
        }
        TerritoryEdge.findAllByBoardMap(boardMap).each { TerritoryEdge edge ->
          Territory source = territoryMap.get(edge.sourceTerritory.name)
          Territory destination = territoryMap.get(edge.destinationTerritory.name)
          if (source && destination) {
            TerritoryEdge newEdge = new TerritoryEdge(sourceTerritory:source, destinationTerritory:destination, boardMap:result).save(failOnError:true)
          }
        }
      } catch (Exception e) {
        log.error(e.getMessage())
        status.setRollbackOnly();
        return null;
      }
    }
    return result
  }

  def export() {
    Long id = params.id as Long
    BoardMap boardMap = BoardMap.get(id)

    StringWriter writer = null

    try {
      writer = new StringWriter()
      CSVWriter csvWriter = new CSVWriter(writer, {
        continent { it.continent }
        territory { it.territory }
        latitude { it.latitude }
        longitude { it.longitude }
      })

      boardMap.continents.each { Continent continent ->
        continent.territories.each { Territory territory ->
          territory.geoLocations.each { GeoLocation geoLocation ->
            csvWriter << [continent:continent.name, territory:territory.name, latitude: geoLocation.latitude, longitude: geoLocation.longitude]
          }
        }
      }

      response.setHeader("Content-disposition", "attachment; filename=" +
          boardMap.name + " Locations.csv");
      render(contentType: "text/csv", text: writer.toString());
    } catch (Exception e) {
      render(status: 403, text: "error exporting boardmap: $params.id: "+e.getMessage())
    } finally {
      IOUtils.closeQuietly(writer)
    }
  }

  def upload() {
    try {
      HttpServletRequest request = request

      MultipartFile file = request.getFile('file')
      if (file.empty) {
        render(status: 403, text: "cannot import empty boardmap file")
        return
      }
      Map params = request.multipartParameters
      def idString = params.id[0]
      Long id = Long.valueOf(idString)
      Map <String, Map<String, List>> locationMap = TerritoryPropertyHelper.loadMapFromReader(file.getInputStream().toCsvReader())

      BoardMap.withTransaction { TransactionStatus status ->
        try {
          BoardMap boardMap = BoardMap.get(id)
          if (boardMap == null) {
            throw new Exception("Boardmap not found for id: $id")
          }

          List territories = Territory.findAll("from Territory t where t.continent.boardMap=?", [boardMap])

          locationMap.each { String territoryName, Map<String, List> map ->
            if (map.geoLocations != null && map.geoLocations.size()>0) {
              Territory territory = territories.find { it.name == territoryName }
              GeoLocation.executeUpdate("delete GeoLocation g where g.territory = ?", [territory])
              territory.geoLocations = map.geoLocations.collect {
                GeoLocation geoLoc = new GeoLocation(latitude:it.latitude, longitude:it.longitude, territory:territory).save(failOnError:true)
              }
            }
          }
        } catch (Exception e) {
          log.error(e.getMessage())
          status.setRollbackOnly();
          response.sendError(500, (e != null?e.getMessage():"null exception"))
          return
        }
      }

      render(status: 200, text: "done")
    } catch (Exception e) {
      render(status: 403, text: "error importing file: "+ e == null?"null exception":e.getMessage())
    }
  }
}
