package com.imperil.setup

import com.dynamix.auth.Requestmap
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

class InitializationHelper {

  public static void initializeData() {
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

    def player1PlayerPreferences = PlayerPreferences.findByName('player1')?:new PlayerPreferences(name: 'player1',description:'player1 description', user: player1User, enabled:true).save(failOnError: true);
    def player2PlayerPreferences = PlayerPreferences.findByName('player2')?:new PlayerPreferences(name: 'player2',description:'player2 description', user: player2User, enabled:true).save(failOnError: true);

    def joePlayerPreferences= PlayerPreferences.findByName('joe')?:new PlayerPreferences(name: 'joe',description:'joe description', user: joeUser, enabled:true).save(failOnError: true);
    def andrewPlayerPreferences = PlayerPreferences.findByName('andrew')?:new PlayerPreferences(name: 'andrew',description:'andrew description', user: andrewUser, enabled:true).save(failOnError: true);

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
    BoardMap boardMap = BoardMap.findByName(DefaultMapConstants.DEFAULT_MAP_NAME)?:
        new BoardMap(name: DefaultMapConstants.DEFAULT_MAP_NAME, description:DefaultMapConstants.DEFAULT_MAP_NAME+' description').save(failOnError: true)
    //    boardMap.continents=[]
    def continents = DefaultMapConstants.CONTINENTS.collect { String continentName, Map<String,Map<String,List<String>>> territoryList ->
      def continent = new Continent(name: continentName, description:continentName+' description', boardMap:boardMap).save(failOnError:true)
      //      boardMap.continents<<continent
      continent.territories=territoryList.collect { name, edges ->new Territory(name: name, description:name+' description', continent:continent).save(failOnError:true)}
    }
    DefaultMapConstants.CONTINENTS.collect { String continentName, Map<String,List<String>> territoryMap ->
      territoryMap.each { sourceName, destinationEdges ->
        Territory source = Territory.findByName(sourceName)
        destinationEdges.each { edgeName ->
          Territory destination = Territory.findByName(edgeName)
          log.trace("createing bi-directional edge between $source.name($source.id) and $destination.name($destination.id)")
          new TerritoryEdge(boardMap:boardMap, sourceTerritory:source, destinationTerritory:destination).save(failOnError:true)
        }
      }
    }

    // END default maps
    boardMap.continents=Continent.findAllByBoardMap(boardMap)
    //sample matches
    [
      'Andrew vs Joe':[
        andrewPlayerPreferences,
        joePlayerPreferences
      ], 'Andrew vs 2 players':[
        andrewPlayerPreferences,
        player1PlayerPreferences,
        player2PlayerPreferences
      ],
      'Joe vs 2 players':[
        joePlayerPreferences,
        player1PlayerPreferences,
        player2PlayerPreferences
      ],
      'Free for All':[
        andrewPlayerPreferences,
        joePlayerPreferences,
        player1PlayerPreferences,
        player2PlayerPreferences
      ]].each {String matchName, List<AppUser> val ->
      List<Player> players = val.collect{ PlayerPreferences pref ->
        new Player(pref)
      }
      def match = Match.findByName(matchName)?:new Match(name: matchName, description:matchName+' description', boardMap:boardMap, players:players).save(failOnError: true);
      log.debug("boardmap has ${boardMap?.continents?.size()} continents")
      List<Territory> territories = boardMap.continents.collect{continent ->
        log.debug("$continent.name: has ${continent.territories?.size()} territories");
        continent.territories}
      territories.flatten().each{ territory ->
        Garrison g = new Garrison(armyCount:0)
        g.match=match
        g.territory = territory
        g.save(failOnError: true)
      }
    }
  }
}