package com.imperil.mapitem

import groovy.transform.ToString

@ToString
class Territory {
  Date dateCreated,lastUpdated
  Integer version
  String name, description

  static belongsTo = [continent:Continent]
  static hasMany = [sourceTerritoryEdges:TerritoryEdge, destinationTerritoryEdges:TerritoryEdge]
  static mappedBy = [sourceTerritoryEdges:'sourceTerritory', destinationTerritoryEdges:'destinationTerritory']
  static constraints = {
    name blank: false
    name unique:true
  }
}
