package com.imperil.player

import java.util.Date;

import com.dynamix.user.AppUser;

class PlayerPreferences {
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  AppUser user

  static belongsTo = [user:AppUser]

  static constraints = {
    name blank: false;
    name unique:true
  }
}
