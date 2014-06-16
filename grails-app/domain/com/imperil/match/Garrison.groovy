package com.imperil.match

import com.imperil.mapitem.Territory
import com.imperil.player.Player

class Garrison {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  Integer armyCount

  static belongsTo = [territory:Territory, match:Match, owner:Player]

  static mappedBy = [territory:'none', match:'none']
  static constraints = {
    territory(nullable:false)
    match(nullable:false)
  }
}
