package com.imperil.player

import grails.converters.JSON

import com.imperil.match.Match

class PlayerController {

  def index() {
  }

  def show() {
    def paramId = params.id
    Player result = Player.get(paramId)
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
