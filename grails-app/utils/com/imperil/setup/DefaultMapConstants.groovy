package com.imperil.setup

class DefaultMapConstants {

  static String DEFAULT_MAP_NAME = "Imperil Default"

  static Map<String, List<String>> NORTH_AMERICA = [
    'Alaska':[
      'Kamchatka',
      'Northwest Territory',
      'Alberta'
    ],
    'Alberta':[
      'Alaska',
      'Northwest Territory',
      'Ontario',
      'Western United States'
    ],
    'Central America':[
      'Western United States',
      'Eastern United States',
      'Venezuela'
    ],
    'Eastern United States':[
      'Central America',
      'Western United States',
      'Ontario',
      'Quebec'
    ],
    'Greenland':[
      'Northwest Territory',
      'Ontario',
      'Quebec',
      'Iceland'
    ],
    'Northwest Territory':[
      'Alaska',
      'Alberta',
      'Ontario',
      'Greenland'
    ],
    'Ontario':[
      'Northwest Territory',
      'Alberta',
      'Western United States',
      'Eastern United States',
      'Quebec',
      'Greenland'
    ],
    'Quebec':[
      'Greenland',
      'Ontario',
      'Eastern United States'
    ],
    'Western United States':[
      'Alberta',
      'Ontario',
      'Eastern United States',
      'Central America']
  ]

  static Map<String, List<String>> SOUTH_AMERICA = [
    'Argentina':[
      'Brazil',
      'Peru',
      'Central America'
    ],
    'Brazil':[
      'Argentina',
      'Peru',
      'Venezuela',
      'North Africa'
    ],
    'Peru':[
      'Argentina',
      'Brazil',
      'Venezuela'
    ],
    'Venezuela':['Brazil', 'Peru']]

  static Map<String, List<String>> EUROPE = [
    'Great Britain':[
      'Iceland',
      'Northern Europe',
      'Scandinavia',
      'Western Europe'
    ],
    'Iceland':[
      'Great Britain',
      'Scandinavia',
      'Greenland'
    ],
    'Northern Europe':[
      'Great Britain',
      'Scandinavia',
      'Southern Europe',
      'Ukraine',
      'Western Europe'
    ],
    'Scandinavia':[
      'Great Britain',
      'Iceland',
      'Northern Europe',
      'Ukraine'
    ],
    'Southern Europe':[
      'Northern Europe',
      'Ukraine',
      'Western Europe',
      'Egypt',
      'North Africa',
      'Middle East'
    ],
    'Ukraine':[
      'Northern Europe',
      'Scandinavia',
      'Southern Europe',
      'Afganistan',
      'Middle East',
      'Ural'
    ],
    'Western Europe':[
      'Great Britain',
      'Northern Europe',
      'Southern Europe',
      'North Africa']
  ]
  static Map<String, List<String>> AFRICA = [
    'Congo':[
      'North Africa',
      'East Africa',
      'South Africa'
    ],
    'East Africa':[
      'Congo',
      'Egypt',
      'Madagascar',
      'North Africa',
      'South Africa',
      'Middle East'
    ],
    'Egypt':[
      'East Africa',
      'North Africa',
      'Southern Europe',
      'Middle East'
    ],
    'Madagascar':[
      'East Africa',
      'South Africa'
    ],
    'North Africa':[
      'Congo',
      'East Africa',
      'Egypt',
      'Southern Europe',
      'Western Europe',
      'Brazil'
    ],
    'South Africa':[
      'Congo',
      'East Africa',
      'Madagascar']
  ]
  static Map<String, List<String>> ASIA = [
    'Afganistan':[
      'Ural',
      'China',
      'India',
      'Middle East',
      'Ukraine'
    ],
    'China':[
      'Afganistan',
      'India',
      'Mongolia',
      'Siam',
      'Siberia',
      'Ural'
    ],
    'India':[
      'Afganistan',
      'China',
      'Middle East',
      'Siam'
    ],
    'Irkutsk':[
      'Kamchatka',
      'Mongolia',
      'Siberia',
      'Yakutsk'
    ],
    'Japan':['Kamchatka', 'Mongolia'],
    'Kamchatka':[
      'Yakutsk',
      'Irkutsk',
      'Mongolia',
      'Japan',
      'Alaska'
    ],
    'Middle East':[
      'Afganistan',
      'India',
      'Southern Europe',
      'Ukraine',
      'East Africa',
      'Egypt'
    ],
    'Mongolia':[
      'China',
      'Irkutsk',
      'Japan',
      'Kamchatka',
      'Siberia'
    ],
    'Siam':[
      'China',
      'India',
      'Indonesia'
    ],
    'Siberia':[
      'Ural',
      'China',
      'Mongolia',
      'Irkutsk',
      'Yakutsk'
    ],
    'Ural':[
      'Siberia',
      'China',
      'Afganistan',
      'Ukraine'
    ],
    'Yakutsk':[
      'Siberia',
      'Irkutsk',
      'Kamchatka']
  ]
  static Map<String, List<String>> AUSTRALIA = [
    'Eastern Australia':[
      'New Guinea',
      'Western Australia'
    ],
    'Indonesia':[
      'New Guinea',
      'Western Australia',
      'Siam'
    ],
    'New Guinea':[
      'Western Australia',
      'Indonesia',
      'Eastern Australia'
    ],
    'Western Australia':[
      'Eastern Australia',
      'Indonesia',
      'New Guinea']
  ]

  static Map<String, Map<String, List<String>>> CONTINENTS = [
    'North America':NORTH_AMERICA,
    'South America':SOUTH_AMERICA,
    'Europe':EUROPE,
    'Africa':AFRICA,
    'Asia':ASIA,
    'Australia':AUSTRALIA
  ]
}
