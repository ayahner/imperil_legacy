package com.imperil.mapitem

class Territory {
  String name, description

  static hasMany = [sourceTerritoryEdges:TerritoryEdge, destinationTerritoryEdges:TerritoryEdge]
  static mappedBy = [sourceTerritoryEdges:'sourceTerritory', destinationTerritoryEdges:'destinationTerritory']
  static constraints = { name blank: false }
}
