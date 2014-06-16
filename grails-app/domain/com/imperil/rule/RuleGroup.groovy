package com.imperil.rule

import com.imperil.match.Match

class RuleGroup {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  String section, name, description
  Map rules = [:]

  static hasMany = [matches:Match, rules:Rule]

  static mapping = { sort "name" }

  static constraints = {
    name blank: false
    name unique:true
  }

  //  @Override
  //  public int hashCode() {
  //    final int prime = 31;
  //    int result = 1;
  //    result = prime * result + ((name == null) ? 0 : name.hashCode());
  //    return result;
  //  }
  //
  //  @Override
  //  public boolean equals(Object obj) {
  //    if (this == obj)
  //      return true;
  //    if (obj == null)
  //      return false;
  //    if (getClass() != obj.getClass())
  //      return false;
  //    RuleGroup other = (RuleGroup) obj;
  //
  //    if (name == null) {
  //      if (other.name != null)
  //        return false;
  //    } else if (!name.equals(other.name))
  //      return false;
  //    return true;
  //  }
}
