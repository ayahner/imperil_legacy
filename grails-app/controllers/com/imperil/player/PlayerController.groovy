package com.imperil.player

import grails.converters.JSON

class PlayerController {

  def index() {
  }

  def list() {
    render Player.list() as JSON
  }
}
