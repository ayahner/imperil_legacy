package com.imperil.player

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

import com.imperil.match.Match

class PlayerController {
  SpringSecurityService  springSecurityService

  def index() {
  }

  def show() {
    def paramId = params.id
    Player result = Player.get(paramId)
    JSON.use('SIMPLE') { render result as JSON }
  }

  def listMine() {
    def currentUser = springSecurityService.currentUser
    def result = Player.findAllByUser(currentUser)
    JSON.use('SIMPLE') { render result as JSON }
  }

  def list() {
    def matchId = params.matchId
    def result
    if (matchId) {
      Match match = Match.get(matchId)
      result = Player.findAllByMatch(match)
    } else {
      result = Player.list()
    }
    JSON.use('SIMPLE') { render result as JSON }
  }
}
