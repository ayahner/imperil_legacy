package com.imperil.mapitem

import java.util.Date;


class Continent {
  Date dateCreated,lastUpdated
  Integer version
  String name, description

  static hasMany = [territories:Territory]
  static constraints = {
    name blank: false
    name unique:true
  }
}
