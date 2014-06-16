package com.imperil.match

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

import com.imperil.mapitem.BoardMap
import com.imperil.mapitem.Territory
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences
import com.imperil.rule.RuleGroup
import com.imperil.rule.RuleHelper
import com.imperil.setup.DefaultMapConstants
import com.imperil.setup.InitializationHelper

class MatchController {

  SpringSecurityService  springSecurityService

  def index() {
  }

  def show() {
    Long paramId = params.id as Long
    Match result = Match.get(paramId)
    JSON.use('DETAILED') { render result as JSON }
  }

  def list() {
    def currentUser = springSecurityService.currentUser
    log.trace("list called for user ${currentUser}")
    def result = Match.list()
    JSON.use('SIMPLE') { render result as JSON }
  }

  def listMine() {
    def currentUser = springSecurityService.currentUser
    log.trace("listMine called for user ${currentUser}")

    def result = Match.withCriteria {
      players { eq 'user.id', currentUser.id }
    }


    JSON.use('SIMPLE') { render result as JSON }
  }

  def save() {
    log.debug("\$request.JSON: $request.JSON")
    List playerPreferenceIds = request.JSON.players.collect {
      Long.valueOf(it.id)
    }
    log.debug("\$params: $params")
    def name=request.JSON.name
    def description=request.JSON.name

    def boardMap = BoardMap.findByName(DefaultMapConstants.DEFAULT_MAP_NAME)
    Set<PlayerPreferences> existingPlayerPreferences = PlayerPreferences.findAllByIdInList(playerPreferenceIds)
    PlayerPreferences myPlayerPreferences = PlayerPreferences.findByUser(springSecurityService.currentUser)
    existingPlayerPreferences.add(myPlayerPreferences)

    RuleGroup ruleGroup = request.JSON.ruleGroupId?RuleGroup.get(request.JSON.ruleGroupId):RuleGroup.getAll().get(0);
    Match match = InitializationHelper.generateMap(name, description, existingPlayerPreferences as List, boardMap, MatchStateEnum.INITIALIZING, ruleGroup)
    RuleHelper.initMatch(match, ruleGroup)
    log.debug("\$match: ${match as JSON})")
    def result = Match.get(match.id) as JSON
    JSON.use('SIMPLE') { render result as JSON }
  }

  def addArmies() {
    log.debug("\$request.JSON: $request.JSON")
    def currentUser = springSecurityService.currentUser
    Long matchId = request.JSON.params.match.id
    Match match = Match.get(matchId)

    Player currentPlayer = Player.get(match.currentPlayer.id)

    //validate current user is the user
    if (currentUser.id != currentPlayer.user.id) {
      log.debug("You are not the correct player to make this action")
      throw new Exception("You are not the correct player to make this action")
      render(status: 403, text: "You are not the correct player to make this action")
      return
    }

    Long territoryId = request.JSON.params.territory.id
    Integer count = request.JSON.params.count

    Territory territory = Territory.get(territoryId)

    log.debug("addArmies(${match.name}, ${currentPlayer.name}, ${territory.name}, ${count})")

    Garrison garrison = Garrison.findByTerritoryAndMatch(territory, match)
    //TODO: add rule for how many armys you can add initially
    if (MatchStateEnum.CHOOSING_TERRITORIES == match.state && garrison.armyCount>0) {
      render(status: 403, text: "${territory.name} is already claimed by ${garrison.owner?.name}")
      return
    }
    currentPlayer.armyCount -= count
    garrison.armyCount += count
    garrison.owner = currentPlayer
    if (!garrison.save()) {
      garrison.errors.allErrors.each { log.error it }
    } else {
      if (!currentPlayer.save()) {
        currentPlayer.errors.allErrors.each { log.error it }
      }
    }
    MatchHelper.nextTurn(match);
    def result = [
      match:Match.get(match.id),
      player:currentPlayer,
      territory:territory,
      garrison:garrison
    ]
    JSON.use('SIMPLE') { render result as JSON }
  }
}
