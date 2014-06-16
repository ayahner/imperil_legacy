/**
 * Edit BoardMap Controller
 */
imperilApp.constant('VIEW_MAP', 'view map')
var EditBoardMapController = function($rootScope, $scope, $http, $routeParams, $log, VIEW_MAP) {
  var mapDiv = document.getElementById("boardMap")

  $scope.boardMap = {}
  $scope.continents = []
  $scope.territories = []
  $scope.edgeMap = {}
  $scope.selectedContinent = {}
  $scope.selectedTerritory = {}

  $scope.lastLatitude = 32.7150
  $scope.lastLongitude = 117.1625
  $scope.lastZoom = 4
  $scope.state = VIEW_MAP

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
    $scope.updateLastFromMap()
    $scope.$digest()
  });

  google.maps.event.addListener(map, 'click', function(mouseEvent) {
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
    // console.log("updateLast lat:"+lat+", lng:"+lng+", z:"+zoom)
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

  $scope.$watch('selectedContinent', function(newValue, oldValue) {
    if (newValue.hasOwnProperty('territories')) {
      $scope.$emit('fitBounds', newValue.territories)
      console.log('emitting \'fitBounds\'')
    }
  }, true);

  $scope.$watch('selectedTerritory', function(newValue, oldValue) {
    if (newValue.hasOwnProperty('geoLocations')) {
      $scope.$emit('fitBounds', [ newValue ])
      console.log('emitting \'fitBounds\'')
    }
  }, true);

  $scope.refresh = function() {
    console.log('refresh called')
    $scope.updateMap($routeParams.id)
  }

  $scope.selectContinent = function(event, continent) {
    var currentTarget = $(event.currentTarget)
    currentTarget.closest('table').find('tr').removeClass('active')
    currentTarget.addClass('active')
    console.log('selecting continent: ' + continent.name)
    $scope.selectedContinent = continent
    $scope.territories = continent.territories
  }

  $scope.selectTerritory = function(event, territory) {
    var currentTarget = $(event.currentTarget)
    currentTarget.closest('table').find('tr').removeClass('active')
    currentTarget.addClass('active')
    console.log('selecting territory: ' + territory.name)
    $scope.selectedTerritory = territory
  }

  $scope.updateMap = function(id) {
    console.log('refresh called')
    $http.get('/boardMap/show', {
      params : {
        id : id
      }
    }).success(function(data, status, headers, config) {
      console.log('success: map: ' + data.name);
      $scope.boardMap = data
      $scope.continents = data.continents
      $scope.edgeMap = data.edgeMap
      $scope.territories = data.edgeMap.territories
    }).error(function(data, status, headers, config) {
      console.log('error in showMatch for id: ' + data.id);
    });
  }

  $scope.refresh()

}
