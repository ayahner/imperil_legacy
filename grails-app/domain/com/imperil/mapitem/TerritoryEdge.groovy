package com.imperil.mapitem

import java.util.Date;


class TerritoryEdge {
  Date dateCreated,lastUpdated
  Integer version

  static hasOne = [sourceTerritory:Territory, destinationTerritory:Territory]
  static belongsTo = [boardMap:BoardMap]

  static constraints = {
  }
}
