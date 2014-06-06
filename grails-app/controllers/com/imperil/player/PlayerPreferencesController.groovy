package com.imperil.player

import grails.converters.JSON

class PlayerPreferencesController {

  def index() {
  }

  def list() {
    def result = PlayerPreferences.list()
    JSON.use('semideep') { render result as JSON }
  }
}
