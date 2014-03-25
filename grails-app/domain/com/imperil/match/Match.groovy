package com.imperil.match

import groovy.transform.ToString

import org.codehaus.groovy.grails.web.binding.ListOrderedSet

import com.imperil.mapitem.BoardMap
import com.imperil.player.Player

@ToString
class Match {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  List players
  MatchStateEnum state
  BoardMap boardMap
  Player currentPlayer

  static hasMany = [players:Player]

  static mapping = { sort "name" }

  static constraints = {
    name blank: false
    name unique:true
  }
}
