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
    render result as JSON
  }

  def listMine() {
    def currentUser = springSecurityService.currentUser
    def result = Player.findAllByUser(currentUser)
    render result as JSON
  }

  def list() {
    def matchId = params.matchId
    if (matchId) {
      Match match = Match.get(matchId)
      render Player.findAllByMatch(match) as JSON
    } else {
      render Player.list() as JSON
    }
  }
}
