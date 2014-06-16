package com.imperil.mapitem

import com.imperil.match.Garrison


class Territory {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  Garrison garrison

  Set sourceTerritoryEdges = []
  Set destinationTerritoryEdges = []
  Set geoLocations = []

  static belongsTo = [continent:Continent]

  static hasMany = [sourceTerritoryEdges:TerritoryEdge, destinationTerritoryEdges:TerritoryEdge, geoLocations:GeoLocation]
  static mappedBy = [sourceTerritoryEdges:'sourceTerritory', destinationTerritoryEdges:'destinationTerritory']
  static constraints = {
    name blank: false
    name unique: 'continent'
    continent(nullable:false)
  }
  static mapping = { sort "name" }
}
