package com.imperil.mapitem

import java.util.Date;


class Continent {
  Date dateCreated,lastUpdated
  Integer version
  String name, description

  static hasMany = [territories:Territory]
  static belongsTo = [boardMap:BoardMap]
  static mapping = { sort "name" }
  static constraints = {
    name blank: false
    boardMap(nullable:false)
  }
}
