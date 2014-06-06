package com.imperil.match

import com.imperil.mapitem.Territory
import com.imperil.player.Player

class Garrison {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  //  Player
  Integer armyCount

  static belongsTo = [territory:Territory, match:Match, owner:Player]
  static mappedBy = [territory:'none', match:'none']
  static constraints = {
    territory(nullable:false)
    match(nullable:false)
  }

  //  @Override
  //  public int hashCode() {
  //    final int prime = 31;
  //    int result = 1;
  //    result = prime * result + ((territory == null) ? 0 : territory.hashCode());
  //    result = prime * result + ((match == null) ? 0 : match.hashCode());
  //    return result;
  //  }
  //
  //  @Override
  //  public boolean equals(Object obj) {
  //    if (this == obj)
  //      return true;
  //    if (obj == null)
  //      return false;
  //    if (getClass() != obj.getClass())
  //      return false;
  //    Garrison other = (Garrison) obj;
  //
  //    if (territory == null) {
  //      if (other.territory != null)
  //        return false;
  //    } else if (!territory.equals(other.territory))
  //      return false;
  //    if (match == null) {
  //      if (other.match != null)
  //        return false;
  //    } else if (!match.equals(other.match))
  //      return false;
  //    return true;
  //  }

}
