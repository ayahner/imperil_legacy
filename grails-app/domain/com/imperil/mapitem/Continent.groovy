package com.imperil.mapitem



class Continent {
  Date dateCreated,lastUpdated
  Integer version
  String name, description
  List territories
  //BoardMap boardMap

  static hasMany = [territories:Territory]
  static belongsTo = [boardMap:BoardMap]
  static mapping = { sort "name" }
  static constraints = {
    name blank: false
    boardMap(nullable:false)
  }

  //  @Override
  //  public int hashCode() {
  //    final int prime = 31;
  //    int result = 1;
  //    result = prime * result + ((name == null) ? 0 : name.hashCode());
  //    result = prime * boardMap + ((name == null) ? 0 : boardMap.hashCode());
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
  //    Continent other = (Continent) obj;
  //
  //    if (name == null) {
  //      if (other.name != null)
  //        return false;
  //    } else if (!name.equals(other.name))
  //      return false;
  //    if (boardMap == null) {
  //      if (other.boardMap != null)
  //        return false;
  //    } else if (!boardMap.equals(other.boardMap))
  //      return false;
  //    return true;
  //  }
}
