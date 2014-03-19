package com.imperil.match

import groovy.transform.ToString

import org.codehaus.groovy.grails.web.binding.ListOrderedSet

import com.imperil.mapitem.BoardMap
import com.imperil.player.Player

@ToString
class Match {
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  List players

  static hasMany = [players:Player]
  static hasOne = [boardMap:BoardMap, currentPlayer:Player]

  //  static mapping = {
  //  }

  static constraints = {
    name blank: false
    name unique:true
  }
}
