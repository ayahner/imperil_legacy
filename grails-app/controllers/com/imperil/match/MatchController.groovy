package com.imperil.match

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

import com.imperil.mapitem.BoardMap
import com.imperil.mapitem.Continent
import com.imperil.mapitem.Territory
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences
import com.imperil.rules.RuleHelper
import com.imperil.setup.DefaultMapConstants
import com.imperil.setup.InitializationHelper

class MatchController {

  SpringSecurityService  springSecurityService

  def index() {
  }

  def show() {
    def paramId = params.id as Long
    Match match = MatchHelper.loadMatch(paramId)
    JSON.use('semideep') { render match as JSON }
  }

  def list() {
    log.trace("list called for user ${session.user}")
    def results = Match.list()
    JSON.use('deep') { render results as JSON }
  }

  def listMine() {
    def currentUser = springSecurityService.currentUser
    log.trace("listMine called for user ${currentUser}")

    def matches = Match.withCriteria {
      players { eq 'user.id', currentUser.id }
    }

    render matches as JSON
  }

  def save() {
    log.debug("\$request.JSON: $request.JSON")
    List playerPreferenceIds = request.JSON.players.collect {
      Long.valueOf(it.id)
    }

    log.debug("\$params: $params")

    def boardMap = BoardMap.findByName(DefaultMapConstants.DEFAULT_MAP_NAME)
    def existingPlayerPreferences = PlayerPreferences.findAllByIdInList(playerPreferenceIds)
    Collections.shuffle(existingPlayerPreferences)
    Match match = InitializationHelper.generateMap(request.JSON.name, request.JSON.description, existingPlayerPreferences, boardMap)
    RuleHelper.initMatch(match)
    log.debug("\$match: ${match as JSON})")
    render Match.get(match.id) as JSON
  }

  def addArmies() {
    log.debug("\$request.JSON: $request.JSON")
    def currentUser = springSecurityService.currentUser
    Player player = Player.get(request.JSON.params.player.id)

    //validate current user is the user
    if (currentUser.id != player.user.id) {
      log.debug("You are not the correct player to make this action")
      //      throw new Exception("You are not the correct player to make this action")
    }

    Match match = Match.get(request.JSON.params.match.id)
    Territory territory = Territory.get(request.JSON.params.territory.id)
    Integer count = request.JSON.params.count
    log.debug("addArmies(${match.name}, ${player.name}, ${territory.name}, ${count})")
    Garrison garrison = Garrison.findByTerritoryAndMatch(territory, match)
    player.armyCount -= count
    garrison.armyCount += count
    if (!garrison.save()) {
      garrison.errors.allErrors.each { println it }
    } else {
      if (!player.save()) {
        player.errors.allErrors.each { println it }
      }
    }
    MatchHelper.nextTurn(match);
    def result = [
      match:MatchHelper.loadMatch(match.id),
      player:player,
      garrison:garrison
    ]
    JSON.use('semideep') { render result as JSON }
  }
}
