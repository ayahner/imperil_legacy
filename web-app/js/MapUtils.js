/**
 * MapUtils functions
 */
function MapUtils() {
}

MapUtils.DEFAULT_FILL_OPACITY = 0.4
MapUtils.DEFAULT_STROKE_OPACITY = 0.8

MapUtils.DEFAULT_MAP_COLOR_LIST = [ //
colorNameToHex('brown'),//
colorNameToHex('black'),//
colorNameToHex('purple'),//
colorNameToHex('blue'),//
colorNameToHex('green'),//
colorNameToHex('orange'),//
colorNameToHex('red'),//
colorNameToHex('darkblue') ]

MapUtils.convertTerritoriesToBounds = function(territoryArray) {
  var locArray = []

  if (Object.prototype.toString.call(territoryArray) === '[object Array]') {
    $.each(territoryArray, function(idx, item) {
      if (item.hasOwnProperty('geoLocations')) {
        locArray = locArray.concat(MapUtils.convertToGeoLocations(item.geoLocations))
      } else if (item.hasOwnProperty('latitude') && item.hasOwnProperty('longitude')) {
        locArray.push(new google.maps.LatLng(item.latitude, item.longitude));
      } else {
        console.log(item + ' is not a geoLocation object or goLocation collection')
      }
    });
  }

  var result = null
  if (locArray.length > 1) {
    result = new google.maps.LatLngBounds()
    $.each(locArray, function(idx, item) {
      result.extend(item)
    });
  }

  return result;

}

MapUtils.convertToGeoLocations = function(geoLocations) {
  var result = []

  $.each(geoLocations, function(idx, item) {
    if (item.hasOwnProperty('latitude') && item.hasOwnProperty('longitude')) {
      result.push(new google.maps.LatLng(item.latitude, item.longitude));
    }
  });

  return result;
}
