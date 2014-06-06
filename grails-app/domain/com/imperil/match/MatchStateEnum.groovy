package com.imperil.match

public enum MatchStateEnum {
  INITIALIZING("Initializing"),CHOOSING_TERRITORIES("Choosing Territories"),POPULATING_GARRISON("Populating Garrison"),PLAYING("Playing"),COMPLETE("Complete")
  private final String name;

  private MatchStateEnum(String enumString) {
    name = enumString;
  }

  public boolean equalsName(String otherName) {
    return (otherName == null)? false:name.equals(otherName);
  }

  public String toString() {
    return name;
  }
}
