package com.imperil.match

import com.imperil.player.Player;

class Match {
  String name, description

  static hasMany = [players:Player]

  static constraints = { name blank: false }
}
