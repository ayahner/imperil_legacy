package com.imperil.marshalling

import grails.plugin.springsecurity.SpringSecurityService

import com.dynamix.user.AppUser
import com.imperil.mapitem.BoardMap
import com.imperil.mapitem.Continent
import com.imperil.mapitem.GeoLocation
import com.imperil.mapitem.Territory
import com.imperil.mapitem.TerritoryEdge
import com.imperil.match.Garrison
import com.imperil.match.Match
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences


class CustomObjectMarshallers {
  SpringSecurityService  springSecurityService
  List marshallers = []

  def register() {
    marshallers.each{ it.register() }
  }

  /*
   * Simple Marshaller Maps
   */
  Map getReturnMapSimple(BoardMap it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['createdBy'] = it.createdBy
    returnMap['name'] = it.name
    returnMap['description'] = it.description

    def continents = it.continents
    def territories = continents.collect { Continent continent -> continent.territories }.flatten()

    returnMap['totalContinents'] = continents?.size()
    returnMap['totalTerritories'] = territories?.size()
    return returnMap
  }

  Map getReturnMapSimple(Continent it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['name'] = it.name
    returnMap['description'] = it.description
    return returnMap
  }

  Map getReturnMapSimple(Territory it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['name'] = it.name
    returnMap['continent'] = it.continent?.name
    returnMap['description'] = it.description
    returnMap['ownedByMe'] = springSecurityService.currentUser?.id && it.garrison?.owner?.id == springSecurityService.currentUser.id
    returnMap['armyCount'] = it.garrison?.armyCount
    return returnMap
  }

  Map getReturnMapSimple(GeoLocation it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['latitude'] = it.latitude
    returnMap['longitude'] = it.longitude
    return returnMap
  }

  Map getReturnMapSimple(TerritoryEdge it) {
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

  Map getReturnMapSimple(Garrison it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['owner'] = it.owner
    returnMap['armyCount'] = it.armyCount
    return returnMap
  }

  Map getReturnMapSimple(Match it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['name'] = it.name
    returnMap['description'] = it.description
    returnMap['state'] = it.state?.toString()
    returnMap['currentPlayer'] = it.currentPlayer
    AppUser currentPlayerUser = it.currentPlayer?.user
    AppUser currentUser = springSecurityService.currentUser
    boolean isMyTurn = currentUser && currentUser.id == currentPlayerUser?.id
    returnMap['isMyTurn'] = isMyTurn
    return returnMap
  }

  Map getReturnMapSimple(Player it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['name'] = it.name
    returnMap['description'] = it.description
    returnMap['armyCount'] = it.armyCount
    returnMap['user'] = it.user
    return returnMap
  }

  Map getReturnMapSimple(PlayerPreferences it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['name'] = it.name
    returnMap['description'] = it.description
    returnMap['user'] = it.user
    return returnMap
  }

  Map getReturnMapSimple(AppUser it) {
    def returnMap = [:]
    returnMap['id'] = it.id
    returnMap['username'] = it.username
    return returnMap
  }

  /*
   * Detailed Marshaller Maps 
   */
  Map getReturnMap(BoardMap it) {
    def returnMap = getReturnMapSimple(it)
    def continents = it.continents
    def territories = continents.collect { Continent continent -> continent.territories }.flatten()
    def edges = it.territoryEdges
    returnMap['continents'] = continents
    return returnMap
  }

  Map getReturnMap(Continent it) {
    def returnMap = getReturnMapSimple(it)
    returnMap['territories'] = it.territories
    return returnMap
  }

  Map getReturnMap(Territory it) {
    def returnMap = getReturnMapSimple(it)
    returnMap['garrison'] = it.garrison
    returnMap['geoLocations'] = it.geoLocations
    return returnMap
  }

  Map getReturnMap(GeoLocation it) {
    def returnMap = getReturnMapSimple(it)
    return returnMap
  }

  Map getReturnMap(TerritoryEdge it) {
    def returnMap = getReturnMapSimple(it)
    return returnMap
  }

  Map getReturnMap(Garrison it) {
    def returnMap = getReturnMapSimple(it)
    return returnMap
  }

  Map getReturnMap(Match it) {
    def returnMap = getReturnMapSimple(it)
    returnMap['players'] = it.players
    returnMap['boardMap'] = it.boardMap
    def territories = it.boardMap?.continents?.collect { Continent continent -> continent.territories }?.flatten()
    territories.each { Territory t ->
      t.garrison = Garrison.findByTerritoryAndMatch(t, it)
    }

    return returnMap
  }

  Map getReturnMap(Player it) {
    def returnMap = getReturnMapSimple(it)
    return returnMap
  }

  Map getReturnMap(PlayerPreferences it) {
    def returnMap = getReturnMapSimple(it)
    return returnMap
  }

  Map getReturnMap(AppUser it) {
    def returnMap = getReturnMapSimple(it)
    return returnMap
  }

  /*
   * Very Detailed Marshaller Maps
   */
  Map getReturnMapWithEdgeMap(BoardMap it) {
    def returnMap = getReturnMap(it)
    def continents = it.continents
    def territories = it.continents?.collect { it.territories }?.flatten()
    def edges = it.territoryEdges
    returnMap['edgeMap'] = [
      territoryCount:(territories?.size()),
      edgeCount:(edges?.size()),
      territories:territories,
      edges:edges
    ]
    return returnMap
  }
}