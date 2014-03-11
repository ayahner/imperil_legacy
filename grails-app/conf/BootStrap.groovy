
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
import com.imperil.match.Garrison
import com.imperil.match.Match
import com.imperil.player.Player
import com.imperil.setup.DefaultMapConstants


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

    Role playRole = Role.findByAuthority('play')?:new Role(authority: 'play').save(failOnError: true);
    Role modRole = Role.findByAuthority('mod')?:new Role(authority: 'mod').save(failOnError: true);
    Role adminRole = Role.findByAuthority('admin')?:new Role(authority: 'admin').save(failOnError: true);


    // User definitions

    def player1User = AppUser.findByUsername('player1')?:new AppUser(username: 'player1',password:'password',enabled:true).save(failOnError: true);
    def player2User = AppUser.findByUsername('player2')?:new AppUser(username: 'player2',password:'password',enabled:true).save(failOnError: true);

    def mod1User = AppUser.findByUsername('mod1')?:new AppUser(username: 'mod1',password:'password',enabled:true).save(failOnError: true);
    def mod2User = AppUser.findByUsername('mod2')?:new AppUser(username: 'mod2',password:'password',enabled:true).save(failOnError: true);

    def joeUser = AppUser.findByUsername('joe')?:new AppUser(username: 'joe',password:'joe',enabled:true).save(failOnError: true)
    def andrewUser = AppUser.findByUsername('andrew')?:new AppUser(username: 'andrew',password:'andrew',enabled:true).save(failOnError: true)

    def player1Player = Player.findByName('player1')?:new Player(name: 'player1',description:'player1 description', user: player1User, enabled:true).save(failOnError: true);
    def player2Player = Player.findByName('player2')?:new Player(name: 'player2',description:'player2 description', user: player2User, enabled:true).save(failOnError: true);

    def joePlayer= Player.findByName('joe')?:new Player(name: 'joe',description:'joe description', user: joeUser, enabled:true).save(failOnError: true);
    def andrewPlayer = Player.findByName('andrew')?:new Player(name: 'andrew',description:'andrew description', user: andrewUser, enabled:true).save(failOnError: true);

    // END User definitions

    // Default role creation/mapping
    Map<Role, List<AppUser>> roleMap = [:]
    roleMap.put(playRole, [
      player1User,
      player2User,
      joeUser,
      andrewUser
    ])
    roleMap.put(modRole, [
      mod1User,
      mod2User,
      joeUser,
      andrewUser
    ])
    roleMap.put(adminRole, [joeUser, andrewUser])

    roleMap.each() { Role role, List<AppUser> users ->
      for (AppUser user : users) {
        if (!user.authorities.contains(role)){
          new AppUserToRole(appUser: user, role: role).save(failOnError: true);
        }
      }
    }
    // END Default role creation/mapping

    // Requestmap
    Requestmap.findByUrl('/js/**')?:new Requestmap(url: '/js/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/css/**')?:new Requestmap(url: '/css/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/images/**')?:new Requestmap(url: '/images/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/login/**')?:new Requestmap(url: '/login/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/oauth/**')?:new Requestmap(url: '/oauth/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/**')?:new Requestmap(url: '/**', configAttribute: 'IS_AUTHENTICATED_FULLY').save(failOnError:true)
    // END Requestmap

    // Default maps
    Set<Continent> boardMapContinents = []

    DefaultMapConstants.CONTINENTS.each { String continentName, List<String> territoryList ->
      Set<Territory> continentTerritories = []
      territoryList.each {
        Territory territory = Territory.findByName(it)?:new Territory(name: it, description:it+' description').save(failOnError: true);
        continentTerritories.add(territory);
      }
      Continent continent = Continent.findByName(continentName)?:new Continent(name: continentName, description:continentName+' description', territories:continentTerritories).save(failOnError: true)
      boardMapContinents.add(continent)
    }
    BoardMap boardMap = BoardMap.findByName(DefaultMapConstants.DEFAULT_MAP_NAME)?:new BoardMap(name: DefaultMapConstants.DEFAULT_MAP_NAME, description:DefaultMapConstants.DEFAULT_MAP_NAME+' description', continents:boardMapContinents).save(failOnError: true)
    // END default maps


    //sample matches
    ['match1', 'match2'].each { String matchName ->
      def match = Match.findByName(matchName)?:new Match(name: matchName, description:matchName+' description', boardMap:boardMap, players:[andrewPlayer, joePlayer]).save(failOnError: true);
      boardMap.continents.collect{continent -> continent.territories}.flatten().collect{ territory ->
        new Garrison(armyCount:0, territory:territory, match:match)}.each{ it.save(failOnError: true)}
    }
    ['match3', 'match4'].each {
      def match = Match.findByName(it)?:new Match(name: it, description:it+' description', boardMap:boardMap, players:[
        andrewPlayer,
        joePlayer,
        player1Player,
        player2Player
      ]).save(failOnError: true);
      boardMap.continents.collect{continent -> continent.territories}.flatten().collect{ territory ->
        new Garrison(armyCount:0, territory:territory, match:match)}.each{ it.save(failOnError: true)}
    }
    ['match5', 'match6'].each {
      def match = Match.findByName(it)?:new Match(name: it, description:it+' description', boardMap:boardMap, players:[
        andrewPlayer,
        player1Player,
        player2Player
      ]).save(failOnError: true);
      boardMap.continents.collect{continent -> continent.territories}.flatten().collect{ territory ->
        new Garrison(armyCount:0, territory:territory, match:match)}.each{ it.save(failOnError: true)}
    }
    ['match7', 'match8'].each {
      def match = Match.findByName(it)?:new Match(name: it, description:it+' description', boardMap:boardMap, players:[
        joePlayer,
        player1Player,
        player2Player
      ]).save(failOnError: true);
      boardMap.continents.collect{continent -> continent.territories}.flatten().collect{ territory ->
        new Garrison(armyCount:0, territory:territory, match:match)}.each{ it.save(failOnError: true)}
    }
  }

  def destroy = {
  }
}
