import grails.converters.JSON

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.web.context.support.WebApplicationContextUtils

import com.dynamix.organization.Organization
import com.dynamix.user.AppUser;
import com.imperil.mapitem.BoardMap;
import com.imperil.mapitem.Continent;
import com.imperil.mapitem.Territory;
import com.imperil.mapitem.TerritoryEdge;
import com.imperil.match.Match
import com.imperil.player.Player;
import com.imperil.setup.InitializationHelper

class BootStrap {
  def springSecurityService
  GrailsApplication grailsApplication

  def init = { servletContext ->
    def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )

    JSON.createNamedConfig('semideep') { JSONConfig ->
      JSONConfig.registerObjectMarshaller(BoardMap) { BoardMap it ->
        def returnMap = [:]
        returnMap['id'] = it.id
        returnMap['name'] = it.name
        returnMap['description'] = it.description
        def continents = it.continents
        def territories = it.continents.collect { it.territories }.flatten()
        def edges = it.territoryEdges
        returnMap['totalContinents'] = continents?.size()
        returnMap['continents'] = continents
        returnMap['edgeMap'] = [
          territoryCount:(territories?.size()),
          edgeCount:(edges?.size()),
          territories:territories,
          edges:edges
        ]
        return returnMap
      }
      JSONConfig.registerObjectMarshaller(Continent) { Continent it ->
        def returnMap = [:]
        returnMap['id'] = it.id
        returnMap['name'] = it.name
        returnMap['description'] = it.description
        returnMap['territories'] = it.territories
        return returnMap
      }
      JSONConfig.registerObjectMarshaller(Territory) { Territory it ->
        def returnMap = [:]
        returnMap['id'] = it.id
        returnMap['name'] = it.name
        returnMap['continent'] = it.continent?.name
        returnMap['description'] = it.description
        returnMap['armyCount'] = it.garrison?.armyCount
        return returnMap
      }
      JSONConfig.registerObjectMarshaller(TerritoryEdge) { TerritoryEdge it ->
        def returnMap = [:]
        returnMap['id'] = it.id
        returnMap['sourceContinentName'] = it.sourceTerritory?.continent?.name
        returnMap['sourceTerritoryName'] = it.sourceTerritory?.name
        returnMap['sourceTerritoryId'] = it.sourceTerritory?.id
        returnMap['destinationContinentName'] = it.destinationTerritory?.continent?.name
        returnMap['destinationTerritoryName'] = it.destinationTerritory?.name
        returnMap['destinationTerritoryId'] = it.destinationTerritory?.id
        returnMap['boardMapId'] = it.boardMap?.id
        return returnMap
      }
      JSONConfig.registerObjectMarshaller(Match) { Match it ->
        def returnMap = [:]
        returnMap['id'] = it.id
        returnMap['name'] = it.name
        returnMap['description'] = it.description
        returnMap['state'] = it.state.toString()
        returnMap['currentPlayer'] = it.currentPlayer
        returnMap['players'] = it.players
        returnMap['boardMap'] = it.boardMap
        return returnMap
      }
      JSONConfig.registerObjectMarshaller(Player) { Player it ->
        def returnMap = [:]
        returnMap['id'] = it.id
        returnMap['name'] = it.name
        returnMap['description'] = it.description
        returnMap['armyCount'] = it.armyCount
        returnMap['user'] = it.user
        return returnMap
      }
      JSONConfig.registerObjectMarshaller(AppUser) { AppUser it ->
        def returnMap = [:]
        returnMap['id'] = it.id
        returnMap['username'] = it.username
        return returnMap
      }
    }

    // Check whether the test data already exists.
    Random random = new Random();
    long now =System.currentTimeMillis();

    def adminOrganization = Organization.findByLabel('Admin')?:new Organization(label: 'Admin').save(failOnError: true);
    InitializationHelper.initializeData();
  }

  def destroy = {
  }
}
