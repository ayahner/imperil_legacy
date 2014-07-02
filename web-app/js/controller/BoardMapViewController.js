/**
 * BoardMap View Controller
 */
var BoardMapViewController = function($rootScope, $scope, $http, $routeParams, $log) {

  var mapDiv = document.getElementById("boardMap")

  $scope.colorList = MapUtils.DEFAULT_MAP_COLOR_LIST;

  $scope.fillOpacity = MapUtils.DEFAULT_FILL_OPACITY
  $scope.strokeOpacity = MapUtils.DEFAULT_STROKE_OPACITY
  $scope.currentBaseColor = $scope.colorList[0]

  $scope.overlays = []

  $scope.newOverlayNextId = 0;

  $scope.lastLatitude = 32.7150
  $scope.lastLongitude = 117.1625
  $scope.lastZoom = 4

  $scope.newOverlayAddListeners = function(overlay) {
    google.maps.event.addListener(overlay, 'click', function() {
      $rootScope.$emit('overlayClicked', overlay)
    });
  }

  /*
   * Register Listeners to listen for rootScope events
   */
  $rootScope.$on('fitBounds', function(event, territoryArray) {
    $scope.fitBounds(territoryArray)
  })

  $rootScope.$on('setMapOptions', function(event, mapOptions) {
    $scope.setMapOptions(mapOptions)
  })

  $rootScope.$on('updateMapView', function(event, continents) {
    console.log('Map view received updateMapView event')
    $scope.updateMap(continents)
  })

  $rootScope.$on('overlayRemoved', function(event, overlay) {
    if (overlay != null) {
      var index = $scope.overlays.indexOf(overlay)
      if (index > 0) {
        $scope.overlays.splice(index, 1)
      }
      overlay.setMap(null)
    }
  });

  $rootScope.$on('territorySelected', function(event, territory) {
    console.log('Map view received territorySelected event')
    $.each($scope.overlays, function(index, value) {
      if (territory != null && territory.id == value.territory.id) {
        value.setOptions({
          fillOpacity : ($scope.fillOpacity + .4)
        });
      } else {
        value.setOptions({
          fillOpacity : ($scope.fillOpacity)
        });
      }
    })
    var overlay = $scope.getOverlayForTerritory(territory)
    $rootScope.$emit('overlaySelected', overlay)
  })

  $rootScope.$on('enableDrawingManager', function(event) {
    $scope.enableDrawingManager()
  })

  $rootScope.$on('disableDrawingManager', function(event) {
    $scope.disableDrawingManager()
  })

  $scope.getOverlayForTerritory = function(territory) {
    var overlay = null
    if ($scope.overlays != null && $scope.overlays.length > 0) {
      var overlays = $.grep($scope.overlays, function(value, index) {
        return value.territory == territory;
      });
      if (overlays.length > 0)
        overlay = overlays[0]
    }
    return overlay;
  }
  /*
   * Local map view functions
   */
  $scope.updateMap = function(continents) {
    console.log('updateMap called')
    if (continents != null) {
      $.each(continents, function(index, continent) {
        continent.baseColor = $scope.colorList[index]
      });
      $scope.updateMapOverlays(continents)
    }
  }

  $scope.updateMapOverlays = function(continents) {
    $.each($scope.overlays, function(index, overlay) {
      overlay.setMap(null)
    });
    $scope.overlays = []
    $.each(continents, function(index, continent) {
      $.each(continent.territories, function(index, territory) {
        $scope.addTerritoryOverlay(continent, territory)
      });
    });
  }

  $scope.addTerritoryOverlay = function(continent, territory) {
    var coordinates = []
    if (territory.geoLocations.length > 2) {
      $.each(territory.geoLocations, function(index, location) {
        coordinates.push(new google.maps.LatLng(location.latitude, location.longitude))
      });
      var overlay = new google.maps.Polygon({
        paths : coordinates,
        strokeColor : continent.baseColor,
        strokeOpacity : $scope.strokeOpacity,
        strokeWeight : 2,
        fillColor : continent.baseColor,
        fillOpacity : $scope.fillOpacity
      });
      overlay.type = google.maps.drawing.OverlayType.POLYGON
      overlay.territory = territory
      overlay.continent = continent
      overlay.setMap($scope.map);
      $scope.overlays.push(overlay)
      $scope.newOverlayAddListeners(overlay);
    }
  }

  $scope.updateLastFromMap = function() {
    $scope.updateLast($scope.map.getCenter().lat(), $scope.map.getCenter().lng(), $scope.map.getZoom())
  }

  $scope.updateLast = function(lat, lng, zoom) {
    $scope.lastLatitude = lat
    $scope.lastLongitude = lng
    $scope.lastZoom = zoom
  }

  $scope.fitBounds = function(territoryArray) {
    var bounds = MapUtils.convertTerritoriesToBounds(territoryArray)
    if (!(bounds == null)) {
      $scope.map.fitBounds(bounds);
      $scope.updateLastFromMap()
    }
  }

  /*
   * New Overlay
   */
  $scope.newOverlayAddPathListeners = function(overlay, path) {
    google.maps.event.addListener(path, 'insert_at', function() {
      $rootScope.$emit('overlayEdited', overlay)
    });
    google.maps.event.addListener(path, 'remove_at', function() {
      $rootScope.$emit('overlayEdited', overlay)
    });
    google.maps.event.addListener(path, 'set_at', function() {
      $rootScope.$emit('overlayEdited', overlay)
    });
  }

  $scope.newOverlayAddListeners = function(overlay) {
    google.maps.event.addListener(overlay, 'click', function() {
      $rootScope.$emit('overlayClicked', overlay);
    });

    switch (overlay.type) {
    case google.maps.drawing.OverlayType.POLYGON:
      var paths = overlay.getPaths();

      var n = paths.getLength();
      for (var i = 0; i < n; i++) {
        var path = paths.getAt(i);
        $scope.newOverlayAddPathListeners(overlay, path);
      }
      break;
    }
  }

  $scope.onNewOverlay = function(event) {
    $scope.drawingManager.setOptions({
      drawingMode : null
    });
    event.overlay.type = event.type;
    event.overlay.appId = $scope.newOverlayNextId++;
    $scope.newOverlayAddListeners(event.overlay);
    $rootScope.$emit('overlayEdited', event.overlay)
    $rootScope.$emit('overlaySelected', event.overlay)
    $scope.$apply()
    console.log("new overlay created (id = " + event.overlay.appId + ")");
  }
  $scope.enableDrawingManager = function() {
    $scope.drawingManager.setOptions({
      drawingMode : google.maps.drawing.OverlayType.POLYGON
    });
    $scope.drawingManager.setMap($scope.map)
  }

  $scope.disableDrawingManager = function() {
    $scope.drawingManager.setOptions({
      drawingMode : null
    });
    $scope.drawingManager.setMap(null)
  }

  /*
   * Initialize Map and Map Event Listeners
   */
  $scope.mapOptions = {
    zoom : $scope.lastZoom,
    zoomControl : true,
    // maxZoom : 4,
    disableDefaultUI : true,
    center : new google.maps.LatLng($scope.lastLatitude, $scope.lastLongitude)
  }

  $scope.map = new google.maps.Map(mapDiv, $scope.mapOptions);

  google.maps.event.addListener($scope.map, 'bounds_changed', function(event) {
    $scope.updateLastFromMap()
    $scope.$apply()
  });

  google.maps.event.addListener($scope.map, 'click', function(mouseEvent) {
    $scope.lastLatitude = mouseEvent.latLng.lat()
    $scope.lastLongitude = mouseEvent.latLng.lng()
    $scope.$apply()
  });
  
  $scope.drawingManager = new google.maps.drawing.DrawingManager({
    drawingMode : google.maps.drawing.OverlayType.POLYGON,
    drawingControl : true,
    polygonOptions : {
      editable : true,
      draggable : true,
      fillOpacity : MapUtils.DEFAULT_FILL_OPACITY,
      fillColor : MapUtils.DEFAULT_MAP_COLOR_LIST[0],
      strokeOpacity : MapUtils.DEFAULT_STROKE_OPACITY,
      strokeColor : MapUtils.DEFAULT_MAP_COLOR_LIST[0]
    },
    drawingControlOptions : {
      position : google.maps.ControlPosition.TOP_CENTER,
      drawingModes : [ google.maps.drawing.OverlayType.POLYGON ]
    }
  });
  google.maps.event.addListener($scope.drawingManager, 'overlaycomplete', $scope.onNewOverlay)

}
