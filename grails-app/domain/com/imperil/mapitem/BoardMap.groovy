package com.imperil.mapitem

class BoardMap {
  static hasMany = [continents:Continent, territoryEdges:TerritoryEdge]

  String name, description
  static constraints = { name blank: false }
}
