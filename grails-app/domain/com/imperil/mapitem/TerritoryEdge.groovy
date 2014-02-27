package com.imperil.mapitem

class TerritoryEdge {
  static hasOne = [sourceTerritory:Territory, destinationTerritory:Territory]
  static belongsTo = [boardMap:BoardMap]

  static constraints = {
  }
}
