package com.imperil.mapitem


class GeoLocation {
  Long id

  Date dateCreated,lastUpdated
  Integer version

  Double latitude
  Double longitude

  static belongsTo = [territory:Territory]

  static constraints = {
    latitude blank:false
    longitude blank:false
  }
}
