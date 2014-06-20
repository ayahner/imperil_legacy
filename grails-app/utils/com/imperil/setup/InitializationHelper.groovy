package com.imperil.setup

import com.dynamix.auth.Requestmap
import com.dynamix.role.Role
import com.dynamix.user.AppUser
import com.dynamix.usertorole.AppUserToRole
import com.imperil.mapitem.BoardMap
import com.imperil.mapitem.Continent
import com.imperil.mapitem.GeoLocation
import com.imperil.mapitem.Territory
import com.imperil.mapitem.TerritoryEdge
import com.imperil.match.Garrison
import com.imperil.match.Match
import com.imperil.match.MatchStateEnum
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences
import com.imperil.rule.Rule
import com.imperil.rule.RuleGroup
import com.imperil.rule.RuleHelper



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
    Requestmap.findByUrl('/semantic/**')?:new Requestmap(url: '/semantic/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/js/**')?:new Requestmap(url: '/js/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/css/**')?:new Requestmap(url: '/css/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/images/**')?:new Requestmap(url: '/images/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/login/**')?:new Requestmap(url: '/login/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/oauth/**')?:new Requestmap(url: '/oauth/**', configAttribute: 'permitAll').save(failOnError:true)
    Requestmap.findByUrl('/**')?:new Requestmap(url: '/**', configAttribute: 'IS_AUTHENTICATED_FULLY').save(failOnError:true)
    // END Requestmap

    //Default Rules
    RuleGroup ruleGroup = new RuleGroup(name:RuleConstants.DEFAULT_RULE_GROUP_NAME, description:RuleConstants.DEFAULT_RULE_GROUP_NAME);
    ruleGroup.rules=[:]
    RuleConstants.DEFAULT_STARTING_ARMY_COUNT.eachWithIndex { count, i ->
      ruleGroup.rules.put("${RuleConstants.RULE_KEY_STARTING_ARMY_COUNT}.${(i+3)}", new Rule(ruleGroup:ruleGroup, key:"${RuleConstants.RULE_KEY_STARTING_ARMY_COUNT}.${(i+3)}", value:count, type:Integer.class, name:"Starting armies for ${(i+3)} players"))
    }
    ruleGroup.save(failOnError:true)
    //END Default Rules

    Map <String, Map<String, List>> defaultLocationMap = TerritoryPropertyHelper.loadMapFromFile(DefaultMapConstants.DEFAULT_MAP_NAME)

    // Default maps
    final BoardMap boardMap = BoardMap.findByName(DefaultMapConstants.DEFAULT_MAP_NAME)?:
        new BoardMap(name: DefaultMapConstants.DEFAULT_MAP_NAME, description:DefaultMapConstants.DEFAULT_MAP_NAME+' description', createdBy: andrewUser).save(failOnError: true)
    def continents = DefaultMapConstants.CONTINENTS.collect { String continentName, Map<String,Map<String,List<String>>> territoryList ->
      def continent = new Continent(name: continentName, description:continentName+' description', boardMap:boardMap).save(failOnError:true)
      continent.territories=territoryList.collect { name, edges ->
        Territory territory = new Territory(name: name, description:name+' description', continent:continent).save(failOnError:true)
        Map<String, List> territoryPropertyMap = defaultLocationMap.get(name);
        List locations = territoryPropertyMap?.geoLocations;
        territory.geoLocations = locations.collect {
          GeoLocation geoLoc = new GeoLocation(latitude:it.latitude, longitude:it.longitude, territory:territory).save(failOnError:true)
        }
        return territory
      }
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
    //    boardMap = BoardMap.get(boardMap.id)
    //sample matches
    [
      'Andrew vs Joe vs Player1 Choosing':[
        state:MatchStateEnum.CHOOSING_TERRITORIES, prefList:[
          andrewPlayerPreferences,
          joePlayerPreferences,
          player1PlayerPreferences
        ]
      ], 'Andrew vs 2 players Last Choice':[
        state:MatchStateEnum.CHOOSING_TERRITORIES, prefList:[
          andrewPlayerPreferences,
          player1PlayerPreferences,
          player2PlayerPreferences
        ]
      ],
      'Joe vs 2 players':[
        state:MatchStateEnum.CHOOSING_TERRITORIES, prefList:[
          joePlayerPreferences,
          player1PlayerPreferences,
          player2PlayerPreferences
        ]
      ],
      'Free for All Playing':[
        state:MatchStateEnum.PLAYING, prefList:[
          andrewPlayerPreferences,
          joePlayerPreferences,
          player1PlayerPreferences,
          player2PlayerPreferences
        ]
      ]].each {String matchName, Map<String, Object> prefMap ->
      //      BoardMap copy = BoardMapController.cloneBoardMap(matchName, matchName, boardMap, boardMap.createdBy)
      InitializationHelper.generateMap(matchName, matchName, prefMap.prefList, boardMap, prefMap.state, ruleGroup)
    }
  }

  public static Match generateMap(String matchName, String matchDescription, List<PlayerPreferences> prefs, BoardMap boardMap, MatchStateEnum state, RuleGroup ruleGroup) {
    List<Player> players = prefs.collect{ PlayerPreferences pref ->
      new Player(pref)
    }
    Collections.shuffle(players)
    Match match = new Match(name: matchName, description:matchDescription, boardMap:boardMap, players:players, state:state).save(failOnError: true);

    if (state==MatchStateEnum.PLAYING) {
      //init playing map to 0 armies per player
      match.players.each { Player player ->
        player.armyCount = 0
        player.save(failOnError:true)
      }
    } else {
      //init normal map to n armies per player
      RuleHelper.initMatch(match, ruleGroup)
    }
    match.currentPlayer=players.get(0)
    match.save(failOnError:true)
    List<Territory> territories = boardMap.continents.collect{Continent continent -> continent.territories }
    int playerIndex = 0
    List flatList = territories.flatten()
    flatList.each{ territory ->
      Player player = players.get(playerIndex++);
      if (playerIndex>=players.size()) playerIndex = 0
      Garrison garrison = new Garrison(
          match:match,
          territory:territory,
          armyCount:0,
          );
      if (state==MatchStateEnum.PLAYING) {
        //init playing map territories to all armies per garrison
        garrison.owner = player
        Rule rule = ruleGroup.rules?.get(RuleConstants.RULE_KEY_STARTING_ARMY_COUNT+"."+match.players.size())
        int playerArmyCount = RuleHelper.getValueAsInteger(rule, 10)

        garrison.armyCount=Math.floor(players.size()*playerArmyCount/territories.size())
      }

      garrison.save(failOnError: true)
    }
    return match
  }
}