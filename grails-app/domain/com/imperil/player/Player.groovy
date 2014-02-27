package com.imperil.player

import com.dynamix.user.AppUser;

class Player {
  String name, description

  static belongsTo = [user:AppUser]

  static constraints = { name blank: false }
}
