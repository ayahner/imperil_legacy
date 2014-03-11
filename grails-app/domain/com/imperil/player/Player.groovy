package com.imperil.player

import grails.converters.JSON
import groovy.transform.ToString

import com.dynamix.user.AppUser

@ToString(excludes="dateCreated,lastUpdated,version,user")
class Player {
  Date dateCreated,lastUpdated
  Integer version
  String name, description

  static belongsTo = [user:AppUser]

  static constraints = {
    name blank: false;
    name unique:true
  }
}
