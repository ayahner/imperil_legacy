/**
 * MapUtils functions
 */
function MapUtils() {
}

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

  console.log('found '+locArray.length+' geoLocation points')

  var result = new google.maps.LatLngBounds()

  $.each(locArray, function(idx, item) {
    console.log('found '+ item)
    result.extend(item)
  });

  console.log('bounds are: '+ result)
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
