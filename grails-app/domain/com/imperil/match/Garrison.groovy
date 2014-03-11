package com.imperil.match

import java.util.Date;

import com.imperil.mapitem.Territory;

class Garrison {
  Date dateCreated,lastUpdated
  Integer version

  Long armyCount

  static belongsTo = [territory:Territory, match:Match]
  static mappedBy = [territory:'none', match:'none']
}
