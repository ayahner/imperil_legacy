package com.imperil.mapitem

import grails.converters.JSON

class TerritoryController {

  def index() {
  }

  def list() {
    log.debug(params.continentId)
    def result
    if (params.continentId) {
      def continent = Continent.get(params.continentId)
      result = Territory.findAllByContinent(continent)
    } else {
      result = Territory.list()
    }
    render result as JSON
  }
}
