/**
 * Edit BoardMap Controller
 */
var EditBoardMapController = function($rootScope, $scope, $http, $routeParams, $log) {
  var POLYGON = google.maps.drawing.OverlayType.POLYGON
  var EDIT_FILL_OPACITY = 0.4
  var EDIT_FILL_OPACITY_STRONG = 0.6
  var EDIT_FILL_COLOR = "ff0000"
  var EDIT_STROKE_OPACITY = 0.8
  var EDIT_STROKE_COLOR = "ff0000"

  var mapDiv = document.getElementById("boardMap")

  $scope.colorList = MapUtils.DEFAULT_MAP_COLOR_LIST;

  $scope.fillOpacity = EDIT_FILL_OPACITY
  $scope.strokeOpacity = EDIT_STROKE_OPACITY

  $scope.boardMap = null
  $scope.continents = []
  $scope.territories = []
  $scope.edgeMap = null
  $scope.selectedContinent = null
  $scope.selectedTerritory = null
  $scope.overlays = []

  $scope.selectedOverlayPendingSave = false

  $scope.currentBaseColor = $scope.colorList[0]
  $scope.selectedOverlay = null;

  $scope.newOverlayNextId = 0;

  $scope.lastLatitude = 32.7150
  $scope.lastLongitude = 117.1625
  $scope.lastZoom = 4

  $scope.onOverlayEdited = function(overlay) {
    console.log(overlay.appId + ": overlay edited");
    $scope.selectedOverlayPendingSave = true
  }

  $scope.onOverlayClicked = function(overlay) {
    $scope.selectedOverlay = overlay
    if (overlay.territory != null) {
      $scope.selectedTerritory = overlay.territory
      $scope.selectedContinent = overlay.continent
    }
    $scope.$apply()
  }

  /*
   * New Overlay
   */
  $scope.newOverlayAddPathListeners = function(overlay, path) {
    google.maps.event.addListener(path, 'insert_at', function() {
      $scope.onOverlayEdited(overlay)
    });
    google.maps.event.addListener(path, 'remove_at', function() {
      $scope.onOverlayEdited(overlay)
    });
    google.maps.event.addListener(path, 'set_at', function() {
      $scope.onOverlayEdited(overlay)
    });
  }

  $scope.newOverlayAddListeners = function(overlay) {
    google.maps.event.addListener(overlay, 'click', function() {
      $scope.onOverlayClicked(overlay);
    });

    switch (overlay.type) {
    case POLYGON:
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
    $scope.selectedOverlay = event.overlay;
    $scope.selectedOverlay.type = event.type;
    $scope.selectedOverlay.appId = $scope.newOverlayNextId++;
    $scope.newOverlayAddListeners($scope.selectedOverlay);
    $scope.newOverlayAddPathListeners($scope.selectedOverlay);
    $scope.selectedOverlayPendingSave = true
    $scope.$apply()
    console.log("new overlay created (id = " + $scope.selectedOverlay.appId + ")");
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

  /*
   * Map Utility Functions
   */
  $scope.selectContinent = function(event, continent) {
    $scope.selectedContinent = continent
    $scope.selectTerritory(event, null, continent)
  }

  $scope.selectTerritory = function(event, territory, continent) {
    $scope.selectedOverlay = null
    $scope.selectedTerritory = territory
    $scope.selectedContinent = continent
  }

  $scope.refresh = function() {
    console.log('refresh called')
    $scope.updateMap($routeParams.id)
  }

  $scope.updateMap = function(id) {
    console.log('updateMap called')
    $http.get('/boardMap/show', {
      params : {
        id : id
      }
    }).success(function(data, status, headers, config) {
      console.log('success: map: ' + data.name);
      $scope.boardMap = data
      $scope.continents = data.continents
      $.each($scope.continents, function(index, continent) {
        continent.baseColor = $scope.colorList[index]
      });
      $scope.edgeMap = data.edgeMap
      $scope.updateMapOverlays()
    }).error(function(data, status, headers, config) {
      console.log('error in showMatch for id: ' + data.id);
    });
  }

  $scope.updateMapOverlays = function() {
    $.each($scope.overlays, function(index, overlay) {
      overlay.setMap(null)
    });
    $scope.overlays = []
    $.each($scope.continents, function(index, continent) {
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
    // console.log("updateLast lat:" + lat + ", lng:" + lng + ", z:" + zoom)
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
   * Scope property watchers
   */
  $scope.$watch('continents', function(newValue, oldValue) {
    if ($scope.selectedContinent != null) {
      var newSelectedContinent = $.grep(newValue, function(e) {
        return e.id == $scope.selectedContinent.id;
      });
      if (newSelectedContinent.length > 0) {
        $scope.selectedContinent = newSelectedContinent[0]
        $scope.territories = $scope.selectedContinent.territories
      }
    }
  })

  $scope.$watch('territories', function(newValue, oldValue) {
    if ($scope.selectedTerritory != null) {
      var newSelectedTerritory = $.grep(newValue, function(e) {
        return e.id == $scope.selectedTerritory.id;
      });
      if (newSelectedTerritory.length > 0) {
        $scope.selectedTerritory = newSelectedTerritory[0]
      }
    }
  })

  $scope.$watch('selectedContinent', function(newValue, oldValue) {
    if (newValue != null) {
      $scope.territories = newValue.territories
      $scope.$emit('fitBounds', newValue.territories)
      console.log('emitting \'fitBounds\'')
    }
  }, true);

  $scope.$watch('selectedTerritory', function(newVal, oldVal) {
    $.each($scope.overlays, function(index, value) {
      if (newVal != null && newVal.id == value.territory.id) {
        value.setOptions({fillOpacity : ($scope.fillOpacity + .4)});
      } else {
        value.setOptions({fillOpacity : ($scope.fillOpacity)});
      }
    })
    if (newVal != null) {
      if ($scope.selectedOverlay == null) {
        $scope.enableDrawingManager()
      }
      $scope.$emit('fitBounds', [ newVal ])
      console.log('emitting \'fitBounds\'')
    }
  });

  $scope.$watch('selectedOverlay', function(newVal, oldVal) {
    if (oldVal != null) {
      oldVal.setEditable(false);
      if (!$.inArray(oldVal, $scope.overlays)) {
        oldVal.setMap(null);
        delete oldVal;
      }
    }
    if (newVal != null) {
      newVal.setEditable(true);
      $scope.drawingManager.setOptions({
        drawingMode : null
      });
      $scope.drawingManager.setMap(null)
    } else {
      $scope.drawingManager.setOptions({
        drawingMode : google.maps.drawing.OverlayType.POLYGON
      });
      if ($scope.selectedTerritory != null) {
        var existingOverlay = $.grep($scope.overlays, function(e) {
          return e.id == $scope.selectedTerritory.id;
        });
        if (existingOverlay.length > 0)
          $scope.drawingManager.setMap($scope.map)
      }
    }
  });

  /*
   * Map button handlers
   */
  $scope.deleteOverlay = function() {
    $scope.clearSelectedOverlay()
    $scope.saveOverlayChanges()
  }

  $scope.cancelOverlayChanges = function() {
    $scope.clearSelectedOverlay()
    $scope.addTerritoryOverlay($scope.selectedContinent, $scope.selectedTerritory)
    $scope.enableDrawingManager()
  }

  $scope.saveOverlayChanges = function() {
    console.log("saveOverlay")

    var geoLocations = []
    if ($scope.selectedOverlay != null) {
      $.each($scope.selectedOverlay.getPath().getArray(), function(index, value) {
        var geoLocation = new Object()
        geoLocation.latitude = value.lat();
        geoLocation.longitude = value.lng();
        console.log("new GeoLocation(latitude:" + geoLocation.latitude + ",longitude:" + geoLocation.longitude + "),")
        geoLocations.push(geoLocation)
      });
    }
    if ($scope.selectedTerritory != null) {
      $http.post('/boardMap/updateGeoLocations', {
        id : $scope.selectedTerritory.id,
        locations : geoLocations
      }).success(function(data, status, headers, config) {
        console.log('success in updateGeoLocations with id: ' + data.id);
        $scope.clearSelectedOverlay()
        $scope.refresh()

      }).error(function(data, status, headers, config) {
        console.log('error in updateGeoLocations for id: ' + data.id);
      });
    }
  }

  /*
   * Map Helper functions
   */
  $scope.clearSelectedOverlay = function() {
    if ($scope.selectedOverlay != null) {
      var index = $scope.overlays.indexOf($scope.selectedOverlay)
      if (index > 0) {
        $scope.overlays.splice(index, 1)
      }
      $scope.selectedOverlay.setMap(null)
      $scope.selectedOverlay = null
    }
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

  $scope.drawingManager = new google.maps.drawing.DrawingManager({
    drawingMode : google.maps.drawing.OverlayType.POLYGON,
    drawingControl : true,
    polygonOptions : {
      editable : true,
      draggable : true,
      fillOpacity : EDIT_FILL_OPACITY,
      fillColor : EDIT_FILL_COLOR,
      strokeOpacity : EDIT_STROKE_OPACITY,
      strokeColor : EDIT_STROKE_COLOR
    },
    drawingControlOptions : {
      position : google.maps.ControlPosition.TOP_CENTER,
      drawingModes : [ google.maps.drawing.OverlayType.POLYGON ]
    }
  });

  $scope.map = new google.maps.Map(mapDiv, $scope.mapOptions);

  google.maps.event.addListener($scope.drawingManager, 'overlaycomplete', $scope.onNewOverlay);

  google.maps.event.addListener($scope.map, 'bounds_changed', function(event) {
    $scope.updateLastFromMap()
    $scope.$apply()
  });

  google.maps.event.addListener($scope.map, 'click', function(mouseEvent) {
    $scope.lastLatitude = mouseEvent.latLng.lat()
    $scope.lastLongitude = mouseEvent.latLng.lng()
    $scope.selectedOverlay = null
    $scope.$apply()
  });

  /*
   * Refresh the page
   */
  $scope.refresh()
}
