package com.imperil.match

import grails.converters.JSON
import groovy.transform.ToString

import com.imperil.mapitem.BoardMap
import com.imperil.player.Player

@ToString
class Match {
  Date dateCreated,lastUpdated
  Integer version
  String name, description

  static hasMany = [players:Player]
  static hasOne = [boardMap:BoardMap]

  static constraints = {
    name blank: false
    name unique:true
  }
}
