
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.web.context.support.WebApplicationContextUtils

import com.dynamix.auth.Requestmap
import com.dynamix.organization.Organization
import com.dynamix.role.Role
import com.dynamix.user.AppUser
import com.dynamix.usertorole.AppUserToRole
import com.imperil.mapitem.BoardMap
import com.imperil.mapitem.Continent
import com.imperil.mapitem.Territory
import com.imperil.mapitem.TerritoryEdge
import com.imperil.match.Garrison
import com.imperil.match.Match
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences
import com.imperil.setup.DefaultMapConstants
import com.imperil.setup.InitializationHelper;


class BootStrap {
  def springSecurityService
  GrailsApplication grailsApplication

  def init = { servletContext ->
    def springContext = WebApplicationContextUtils.getWebApplicationContext( servletContext )

    springContext.getBean( "customObjectMarshallers" ).register()

    // Check whether the test data already exists.
    Random random = new Random();
    long now =System.currentTimeMillis();

    def adminOrganization = Organization.findByLabel('Admin')?:new Organization(label: 'Admin').save(failOnError: true);
    InitializationHelper.initializeData();
  }

  def destroy = {
  }
}
