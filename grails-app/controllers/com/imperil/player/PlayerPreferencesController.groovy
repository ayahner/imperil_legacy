package com.imperil.player

import grails.converters.JSON

class PlayerPreferencesController {

  def index() {
  }

  def list() {
    render PlayerPreferences.list() as JSON
  }
}
