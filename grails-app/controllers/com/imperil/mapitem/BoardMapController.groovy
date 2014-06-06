package com.imperil.mapitem

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

class BoardMapController {

  SpringSecurityService  springSecurityService

  def index() {
  }

  def show() {
    def paramId = params.id
    BoardMap result = BoardMap.get(paramId)
    render result as JSON
  }

  def listMine() {
    def currentUser = springSecurityService.currentUser
    log.trace("listMine called for user ${currentUser}")

    def maps = BoardMap.findAllByCreatedBy(currentUser)

    render maps as JSON
  }
}
