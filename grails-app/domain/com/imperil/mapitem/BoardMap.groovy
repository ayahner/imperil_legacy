package com.imperil.mapitem

import com.dynamix.user.AppUser
import com.imperil.match.Match

class BoardMap {
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  List continents
  AppUser createdBy

  static hasMany = [continents:Continent, territoryEdges:TerritoryEdge, matches:Match]

  static constraints = {
    name blank: false
    name unique:true
  }
  static mapping = { sort "name" }

  //  @Override
  //  public int hashCode() {
  //    final int prime = 31;
  //    int result = 1;
  //    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
  //    BoardMap other = (BoardMap) obj;
  //
  //    if (name == null) {
  //      if (other.name != null)
  //        return false;
  //    } else if (!name.equals(other.name))
  //      return false;
  //    return true;
  //  }
}
