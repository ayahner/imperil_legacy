package com.imperil.match

import com.imperil.mapitem.BoardMap
import com.imperil.player.Player

class Match {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  List players = []
  MatchStateEnum state
  BoardMap boardMap
  Player currentPlayer

  static hasMany = [players:Player]

  static mapping = { sort "name" }

  static constraints = {
    name blank: false
    name unique:true
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false
    if (this.is(obj)) return true
    if (!(obj instanceof Match)) return false
    if (!obj.canEqual(this)) return false
    if (name != obj.name) return false
    return true;
  }

  public boolean canEqual(java.lang.Object other) {
    return other instanceof Match
  }
}
