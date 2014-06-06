package com.imperil.rule


class Rule {
  Long id
  Date dateCreated,lastUpdated
  Integer version
  Class type = String.class
  String key, value, name, description
  static belongsTo = [ruleGroup:RuleGroup]

  static constraints = {
    key blank: false
    value blank: false
    name blank: false
    name unique: 'ruleGroup'
  }

  //  @Override
  //  public int hashCode() {
  //    final int prime = 31;
  //    int result = 1;
  //    result = prime * result + ((key == null) ? 0 : key.hashCode());
  //    result = prime * result + ((value == null) ? 0 : value.hashCode());
  //    result = prime * result + ((name == null) ? 0 : name.hashCode());
  //    result = prime * result + ((ruleGroup == null) ? 0 : ruleGroup.hashCode());
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
  //    Rule other = (Rule) obj;
  //
  //    if (key == null) {
  //      if (other.key != null)
  //        return false;
  //    } else if (!key.equals(other.key))
  //      return false;
  //    if (value == null) {
  //      if (other.value != null)
  //        return false;
  //    } else if (!value.equals(other.value))
  //      return false;
  //    if (name == null) {
  //      if (other.name != null)
  //        return false;
  //    } else if (!name.equals(other.name))
  //      return false;
  //    if (ruleGroup == null) {
  //      if (other.ruleGroup != null)
  //        return false;
  //    } else if (!ruleGroup.equals(other.ruleGroup))
  //      return false;
  //    return true;
  //  }
}
