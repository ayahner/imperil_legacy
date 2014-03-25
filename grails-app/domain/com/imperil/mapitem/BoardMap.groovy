package com.imperil.mapitem

import com.imperil.match.Match;

class BoardMap {
  Date dateCreated,lastUpdated
  Integer version
  String name, description

  static hasMany = [continents:Continent, territoryEdges:TerritoryEdge, matches:Match]

  static constraints = {
    name blank: false
    name unique:true
  }
  static mapping = { sort "name" }
}
