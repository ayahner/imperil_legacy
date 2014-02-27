package com.imperil.setup

class DefaultMapConstants {

  static String DEFAULT_MAP_NAME = "Imperil Default"

  static List<String> NORTH_AMERICA = [
    'Alaska',
    'Alberta',
    'Central America',
    'Eastern United States',
    'Greenland',
    'Northwest Terrirory',
    'Ontario',
    'Quebec',
    'Western United States'
  ]

  static List<String> SOUTH_AMERICA = [
    'Argentina',
    'Brazil',
    'Peru',
    'Venezuela'
  ]
  static List<String> EUROPE = [
    'Great Britain',
    'Iceland',
    'Northern Europe',
    'Scandinavia',
    'Southern Europe',
    'Ukraine',
    'Western Europe'
  ]
  static List<String> AFRICA = [
    'Congo',
    'East Africa',
    'Egypt',
    'Madagascar',
    'North Africa',
    'South Africa'
  ]
  static List<String> ASIA = [
    'Afganistan',
    'China',
    'India',
    'Irkutsk',
    'Japan',
    'Kamchatka',
    'Middle East',
    'Mongolia',
    'Siam',
    'Siberia',
    'Ural',
    'Yakutsk'
  ]
  static List<String> AUSTRALIA = [
    'Eastern Australia',
    'Indonesia',
    'New Guinea',
    'Western Australia'
  ]

  static Map<String, List<String>> CONTINENTS = [
    'North America':NORTH_AMERICA,
    'North America':SOUTH_AMERICA,
    'Europe':EUROPE,
    'Africa':AFRICA,
    'Asia':ASIA,
    'Australia':AUSTRALIA
  ]
}
