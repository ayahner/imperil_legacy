package com.imperil.mapitem

import com.dynamix.user.AppUser
import com.imperil.match.Match

class BoardMap {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  List continents = []
  AppUser createdBy

  static hasMany = [continents:Continent, territoryEdges:TerritoryEdge, matches:Match]

  static constraints = {
    name blank: false
    name unique:true
  }
  static mapping = { sort "name" }
}
