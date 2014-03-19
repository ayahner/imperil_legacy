package com.imperil.match

import grails.plugin.springsecurity.SpringSecurityUtils
import grails.test.mixin.*

import com.dynamix.user.AppUser
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences

class MatchControllerIntegrationTests extends GroovyTestCase {

  static final String MATCH = 'match'
  static final String MOCKED_USER = 'player1'
  static def springSecurityService

  def setup() {
  }

  def cleanup() {
  }

  void testMatchController() {
    def mc = new MatchController()
    SpringSecurityUtils.doWithAuth(MOCKED_USER) {
      def match = Match.findByName(MATCH)
      def matches = mc.listMine()
    }
  }
}