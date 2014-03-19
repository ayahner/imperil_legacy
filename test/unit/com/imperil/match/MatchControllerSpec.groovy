package com.imperil.match

import grails.plugin.springsecurity.SpringSecurityService
import grails.test.mixin.*
import spock.lang.Specification

import com.dynamix.user.AppUser
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(MatchController)
@Mock([AppUser, PlayerPreferences, Player, Match])
class MatchControllerSpec extends Specification {

  static final String MATCH = 'match'
  static final AppUser MOCKED_USER = new AppUser(username: 'player1',password:'password',enabled:true)

  def setup() {
    def springSecurityServiceMock = mockFor(SpringSecurityService)
    springSecurityServiceMock.demand.getCurrentUser {-> MOCKED_USER }
    controller.springSecurityService = springSecurityServiceMock.createMock()
  }

  def cleanup() {
  }

  void "test something"() {
    AppUser.metaClass.encodePassword = { -> }
    when:
    def player1User = AppUser.findByUsername(MOCKED_USER.username)?:MOCKED_USER.save(failOnError: true);
    def player2User = AppUser.findByUsername('player2')?:new AppUser(username: 'player2',password:'password',enabled:true).save(failOnError: true);
    def player1PlayerPreferences = PlayerPreferences.findByName('player1')?:new PlayerPreferences(name: 'player1',description:'player1 description', user: player1User, enabled:true).save(failOnError: true);
    def player2PlayerPreferences = PlayerPreferences.findByName('player2')?:new PlayerPreferences(name: 'player2',description:'player2 description', user: player2User, enabled:true).save(failOnError: true);
    [
      '2 players':[
        player1PlayerPreferences,
        player2PlayerPreferences
      ]
    ].each { String matchName, List<AppUser> val ->
      List<Player> players = val.collect{ PlayerPreferences pref ->
        new Player(pref)
      }
      def match = Match.findByName(MATCH)?:new Match(name: MATCH, description:matchName+' description', players:players).save(failOnError: true);
    }

    then:
    def match = Match.findByName(MATCH)
    def matches = controller.listMine()
    match != null
  }
}