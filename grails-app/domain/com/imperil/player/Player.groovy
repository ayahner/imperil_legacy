package com.imperil.player

import groovy.transform.ToString

import com.dynamix.user.AppUser
import com.imperil.match.Match

@ToString(excludes="dateCreated,lastUpdated,version,user")
class Player {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  Integer armyCount
  Match match
  AppUser user

  Player(PlayerPreferences preferences) {
    this.name=preferences.name
    this.description=preferences.description
    this.user=preferences.user
  }

  static constraints = { name blank: false; }
}
