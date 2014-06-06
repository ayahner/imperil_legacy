package com.imperil.mapitem



class TerritoryEdge {
  Date dateCreated,lastUpdated
  Integer version

  static hasOne = [sourceTerritory:Territory, destinationTerritory:Territory]
  static belongsTo = [boardMap:BoardMap]

  static constraints = {
  }

  //  @Override
  //  public int hashCode() {
  //    final int prime = 31;
  //    int result = 1;
  //    result = prime * result + ((sourceTerritory == null) ? 0 : sourceTerritory.hashCode());
  //    result = prime * result + ((destinationTerritory == null) ? 0 : destinationTerritory.hashCode());
  //    result = prime * result + ((boardMap == null) ? 0 : boardMap.hashCode());
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
  //    TerritoryEdge other = (TerritoryEdge) obj;
  //
  //    if (sourceTerritory == null) {
  //      if (other.sourceTerritory != null)
  //        return false;
  //    } else if (!sourceTerritory.equals(other.sourceTerritory))
  //      return false;
  //    if (destinationTerritory == null) {
  //      if (other.destinationTerritory != null)
  //        return false;
  //    } else if (!destinationTerritory.equals(other.destinationTerritory))
  //      return false;
  //    if (boardMap == null) {
  //      if (other.boardMap != null)
  //        return false;
  //    } else if (!boardMap.equals(other.boardMap))
  //      return false;
  //    return true;
  //  }
}
