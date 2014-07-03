package com.imperil.mapitem


class GeoLocation {
  Long id

  Date dateCreated,lastUpdated
  Integer version

  Double latitude
  Double longitude
  Territory territory

  static belongsTo = [territory:Territory]

  static constraints = {
    latitude blank: false
    longitude blank: false
    territory(nullable:false)
  }
}
