package com.imperil.match

import java.util.Date;

import com.imperil.mapitem.Territory;

class Garrison {
  Date dateCreated,lastUpdated
  Integer version

  Integer armyCount

  static belongsTo = [territory:Territory, match:Match]
  static mappedBy = [territory:'none', match:'none']
  static constraints = {
    territory(nullable:false)
    match(nullable:false)
  }
}
