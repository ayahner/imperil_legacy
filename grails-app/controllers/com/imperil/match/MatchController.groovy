package com.imperil.match

import grails.converters.JSON
import grails.plugin.springsecurity.SpringSecurityService

import com.imperil.service.MatchService

class MatchController {

  MatchService matchService
  SpringSecurityService  springSecurityService

  def show() {
    Long paramId = params.id as Long
    Match result = Match.get(paramId)
    JSON.use('DETAILED') { render result as JSON }
  }

  def list() {
    def currentUser = springSecurityService.currentUser
    log.trace("list called for user ${currentUser}")
    def result = Match.list()
    JSON.use('SIMPLE') { render result as JSON }
  }

  def listMine() {
    def currentUser = springSecurityService.currentUser
    log.trace("listMine called for user ${currentUser}")

    def result = Match.withCriteria {
      players { eq 'user.id', currentUser.id }
    }


    JSON.use('SIMPLE') { render result as JSON }
  }

  def save() {
    return matchService.save()
  }

  def addArmies() {
    return matchService.addArmies()
  }
}
