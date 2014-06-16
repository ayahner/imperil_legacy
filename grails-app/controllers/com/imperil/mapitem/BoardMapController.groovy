package com.imperil.mapitem

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

import org.springframework.transaction.TransactionStatus

import com.dynamix.user.AppUser

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
}
