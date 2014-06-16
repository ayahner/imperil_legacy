package com.imperil.mapitem

import grails.converters.JSON

class ContinentController {

  def index() {
  }

  def list () {
    def boardMapId = params.boardMapId
    if (boardMapId) {
      BoardMap boardMap = BoardMap.get(boardMapId)
      List<Continent> result = Continent.findAllByBoardMap(boardMap)
      render result as JSON
    } else {
      List<Continent> result = Continent.findAll()
      JSON.use('SIMPLE') { render result as JSON }
    }
  }
}
