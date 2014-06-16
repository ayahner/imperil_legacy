package com.imperil.player

import com.dynamix.user.AppUser

class PlayerPreferences {
  Long id

  Date dateCreated,lastUpdated
  Integer version
  String name, description
  AppUser user

  static constraints = {
    name blank: false;
    name unique: 'user'
  }

  //  @Override
  //  public int hashCode() {
  //    final int prime = 31;
  //    int result = 1;
  //    result = prime * result + ((name == null) ? 0 : name.hashCode());
  //    result = prime * result + ((user == null) ? 0 : user.hashCode());
  //    return result;
  //  }
  //  @Override
  //  public boolean equals(Object obj) {
  //    if (this == obj)
  //      return true;
  //    if (obj == null)
  //      return false;
  //    if (getClass() != obj.getClass())
  //      return false;
  //    Player other = (Player) obj;
  //
  //    if (name == null) {
  //      if (other.name != null)
  //        return false;
  //    } else if (!name.equals(other.name))
  //      return false;
  //    if (user == null) {
  //      if (other.user != null)
  //        return false;
  //    } else if (!user.equals(other.user))
  //      return false;
  //    return true;
  //  }
}
