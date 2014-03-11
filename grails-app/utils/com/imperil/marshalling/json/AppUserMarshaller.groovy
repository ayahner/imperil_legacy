package com.imperil.marshalling.json

import grails.converters.JSON

import com.dynamix.user.AppUser

class AppUserMarshaller {
  void register() {
    JSON.registerObjectMarshaller(AppUser) { AppUser appUser ->
      return [
        dateCreated : appUser.dateCreated,
        lastUpdated : appUser.lastUpdated,
        version : appUser.version,
        id : appUser.id,
        username : appUser.username,
        //        password : appUser.password,
        enabled : appUser.enabled,
        accountExpired : appUser.accountExpired,
        accountLocked : appUser.accountLocked,
        passwordExpired : appUser.passwordExpired
      ]
    }
  }
}
