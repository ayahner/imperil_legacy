package com.imperil.mapitem

import grails.converters.JSON

class BoardMapController {

  def index() {
  }

  def show() {
    def paramId = params.id
    BoardMap result = BoardMap.get(paramId)
    render result as JSON
  }
}
