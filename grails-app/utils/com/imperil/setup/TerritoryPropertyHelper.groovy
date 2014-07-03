package com.imperil.setup

import org.grails.plugins.csv.CSVMapReader


public class TerritoryPropertyHelper {

  public static Integer DEFAULT_MAX_ZOOM = 4;
  public static Integer DEFAULT_MIN_ZOOM = 4;

  public static Map <String, Map<String, List>> loadMapFromFile(String mapName) {
    Map <String, Map<String, List>> locationMap = [:]
    return loadMapFromCsvReader(locationMap, ResourceLoader.getResourceAsStream("${mapName} Locations.csv").toCsvReader(['charset':'UTF-8']))
  }

  public static Map <String, Map<String, List>> loadMapFromReader(csvReader) {
    Map <String, Map<String, List>> locationMap = [:]
    return loadMapFromCsvReader(locationMap, csvReader)
  }

  public static Map <String, Map<String, List>> loadMapFromCsvReader(Map <String, Map<String, List>> locationMap, csvReader) {
    new CSVMapReader(csvReader).each { map ->
      String name = map['territory']
      if (locationMap.get(name)==null) {
        locationMap.put(name, [geoLocations:[]])
      }
      locationMap.get(name).get('geoLocations').add(map)
    }
    return locationMap
  }
}
