import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.web.context.support.WebApplicationContextUtils

import com.dynamix.organization.Organization
import com.dynamix.user.AppUser
import com.imperil.mapitem.BoardMap
import com.imperil.mapitem.Continent
import com.imperil.mapitem.Territory
import com.imperil.mapitem.TerritoryEdge
import com.imperil.marshalling.CustomObjectMarshallers
import com.imperil.match.Garrison
import com.imperil.match.Match
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences
import com.imperil.setup.InitializationHelper

class BootStrap {
  SpringSecurityService springSecurityService
  CustomObjectMarshallers customObjectMarshallers
  GrailsApplication grailsApplication

  def init = { servletContext ->
    def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )

    JSON.createNamedConfig('semishallow') { JSONConfig ->
      JSONConfig.registerObjectMarshaller(BoardMap) { BoardMap it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Continent) { Continent it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Territory) { Territory it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(TerritoryEdge) { TerritoryEdge it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Garrison) { Garrison it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Match) { Match it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Player) { Player it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(PlayerPreferences) { PlayerPreferences it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(AppUser) { AppUser it ->
        return customObjectMarshallers.getReturnMap(it);
      }
    }

    JSON.createNamedConfig('semideep') { JSONConfig ->
      JSONConfig.registerObjectMarshaller(BoardMap) { BoardMap it ->
        return customObjectMarshallers.getReturnMapWithEdgeMap(it);
      }
      JSONConfig.registerObjectMarshaller(Continent) { Continent it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Territory) { Territory it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(TerritoryEdge) { TerritoryEdge it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Garrison) { Garrison it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Match) { Match it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(Player) { Player it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(PlayerPreferences) { PlayerPreferences it ->
        return customObjectMarshallers.getReturnMap(it);
      }
      JSONConfig.registerObjectMarshaller(AppUser) { AppUser it ->
        return customObjectMarshallers.getReturnMap(it);
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
