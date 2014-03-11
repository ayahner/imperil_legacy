package com.imperil.match

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

import com.imperil.player.Player

class MatchController {

  SpringSecurityService  springSecurityService

  def index() {
  }

  def show() {
    def matchId = params.id
    Match match = Match.get(matchId)
    JSON.use('deep')
    render match as JSON
  }

  def list() {
    log.trace("list called for user ${session.user}")
    def results = Match.list()
    JSON.use('deep') { render results as JSON }
  }

  def listMine() {
    def currentUser = springSecurityService.currentUser
    log.trace("listMine called for user ${currentUser}")
    def query = Match.where {
      players.user.id == currentUser.id
    }
    def results = query.findAll()
    render results as JSON
  }

  def save() {
    log.debug("\$request.JSON: $request.JSON")
    List playerIds = request.JSON.players.collect {
      Long.valueOf(it.id)
    }
    def existingPlayers = Player.findAllByIdInList(playerIds)

    log.debug("\$params: $params")
    def match = new Match(
        name: request.JSON.name,
        description : request.JSON.description,
        players : existingPlayers
        ).save( failOnError : true )
    log.debug("\$match: ${match as JSON})")
    render match as JSON
  }
}
