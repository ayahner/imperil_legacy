package com.imperil.player

import com.dynamix.user.AppUser
import com.imperil.match.Match

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
    this.armyCount=0
  }

  static constraints = {
    name blank: false
    armyCount nullable: false
  }
  static mapping = { sort "name" }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((match == null) ? 0 : match.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false
    if (this.is(obj)) return true
    if (!(obj instanceof Player)) return false
    if (!obj.canEqual(this)) return false
    if (name != obj.name) return false
    if (match != obj.match) return false
    if (user != obj.user) return false
    return true;
  }

  public boolean canEqual(java.lang.Object other) {
    return other instanceof Player
  }
}
