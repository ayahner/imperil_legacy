package com.imperil.mapitem

import groovy.transform.ToString

import com.imperil.match.Garrison

@ToString
class Territory {
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  Garrison garrison

  static belongsTo = [continent:Continent]
  static hasMany = [sourceTerritoryEdges:TerritoryEdge, destinationTerritoryEdges:TerritoryEdge]
  static mappedBy = [sourceTerritoryEdges:'sourceTerritory', destinationTerritoryEdges:'destinationTerritory']
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
  //    Territory other = (Territory) obj;
  //
  //    if (name == null) {
  //      if (other.name != null)
  //        return false;
  //    } else if (!name.equals(other.name))
  //      return false;
  //    return true;
  //  }
}
