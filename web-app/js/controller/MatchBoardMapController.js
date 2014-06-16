/**
 * Match BoardMap Controller
 */
var MatchBoardMapController = function($rootScope, $scope, $http, $routeParams, $log) {
  var mapDiv = document.getElementById("matchMap")

  $scope.lastLatitude = 32.7150
  $scope.lastLongitude = 117.1625
  $scope.lastZoom = 4

  /*
   * Initialize Map
   */
  var mapOptions = {
    zoom : $scope.lastZoom,
    maxZoom : 4,
    center : new google.maps.LatLng($scope.lastLatitude, $scope.lastLongitude)
  }

  var map = new google.maps.Map(mapDiv, mapOptions);

  google.maps.event.addListener(map, 'bounds_changed', function(event) {
    console.log(map.getCenter().lat(), map.getCenter().lng(), map.getZoom())
    $scope.updateLastFromMap()
    $scope.$digest()
  });

  google.maps.event.addListener(map, 'click', function(mouseEvent) {
    console.log('click on map: '+ mouseEvent.latLng)
  $scope.lastLatitude = mouseEvent.latLng.lat()
  $scope.lastLongitude = mouseEvent.latLng.lng()
  $scope.$digest()
  });
  
  /*
   * Initialize Map Event Listeners
   */

  /*
   * Register Map to listen for rootScope events
   */
  $rootScope.$on('fitBounds', function(event, territoryArray) {
    $scope.fitBounds(territoryArray)
  })

  $rootScope.$on('setMapOptions', function(event, mapOptions) {
    $scope.setMapOptions(mapOptions)
  })

  $scope.updateLastFromMap = function() {
    $scope.updateLast(map.getCenter().lat(), map.getCenter().lng(), map.getZoom())
  }

  $scope.updateLast = function(lat, lng, zoom) {
    $scope.lastLatitude = lat
    $scope.lastLongitude = lng
    $scope.lastZoom = zoom
  }

  $scope.fitBounds = function(territoryArray) {
    var bounds = MapUtils.convertTerritoriesToBounds(territoryArray)
    map.fitBounds(bounds);
    $scope.updateLastFromMap()
  }

  /*
   * Map Utility Functions
   */
  $scope.logMap = function() {
    console.log($scope.printMap())
  }

  $scope.printMap = function() {
    return "lastLatitude: " + $scope.lastLatitude + ", lastLongitude: " + $scope.lastLatitude + ", zoom: " + $scope.lastZoom
  }

}
