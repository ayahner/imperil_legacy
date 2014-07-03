package com.imperil.mapitem


class Continent {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  List territories = []

  static hasMany = [territories:Territory]
  static belongsTo = [boardMap:BoardMap]

  static mapping = { sort "name" }
  static constraints = {
    name blank: false
    boardMap(nullable:false)
  }
}
