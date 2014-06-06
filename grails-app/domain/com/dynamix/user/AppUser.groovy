package com.dynamix.user

import grails.converters.JSON

import com.dynamix.oauth.OAuthID
import com.dynamix.organization.Organization
import com.dynamix.role.Role
import com.dynamix.usertorole.AppUserToRole
import com.imperil.player.Player
import com.imperil.player.PlayerPreferences


class AppUser {
  static belongsTo = [organization: Organization]
  static hasMany = [oAuthIDs: OAuthID, players:Player, playerPreferences:PlayerPreferences]

  static constraints = {
    username blank: false, unique: true
    password blank: false
  }

  // JSON definition of the User object
  static {
    grails.converters.JSON.registerObjectMarshaller(AppUser) {
      // you can filter here the key-value pairs to output:
      return it.properties.findAll {k,v -> k != 'password'}
    }

  }

  static mapping = { password column: '`password`' }
  static def displayIgnore = ['password']

  transient springSecurityService

  Date dateCreated
  Date lastUpdated
  Long version

  Long id
  String username
  String password
  boolean enabled
  boolean accountExpired
  boolean accountLocked
  boolean passwordExpired

  Set<Role> getAuthorities() {
    AppUserToRole.findAllByAppUser(this).collect { it.role } as Set
  }

  def beforeInsert() {
    encodePassword()
  }

  def beforeUpdate() {
    if (isDirty('password')) {
      encodePassword()
    }
  }

  protected void encodePassword() {
    password = springSecurityService.encodePassword(password)
  }

  String toString(){
    return username
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((username == null) ? 0 : username.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false
    if (this.is(obj)) return true
    if (!(obj instanceof AppUser)) return false
    if (!obj.canEqual(this)) return false
    if (username != obj.username) return false
    return true;
  }

  public boolean canEqual(java.lang.Object other) {
    return other instanceof AppUser
  }


  //These are here b/c the Controller template cannot refer the Controller instance directly
  static def navStates = AppUserController.navStates
  static def reports = AppUserController.reports
  static def primaryFields = AppUserController.primaryFields;
  static def secondaryFields = AppUserController.secondaryFields;
  static def actions = AppUserController.actions;
  static def defaultLabelProp = AppUserController.defaultLabelProp;

}
