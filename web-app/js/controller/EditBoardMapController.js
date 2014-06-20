/**
 * Edit BoardMap Controller
 */
imperilApp.constant('VIEW_MAP', 'view map')

var EditBoardMapController = function($rootScope, $scope, $http, $routeParams, $log, VIEW_MAP) {
  var POLYGON = google.maps.drawing.OverlayType.POLYGON
  var EDIT_FILL_OPACITY = 0.4
  var EDIT_FILL_COLOR = "ff0000"
  var EDIT_STROKE_OPACITY = 0.8
  var EDIT_STROKE_COLOR = "ff0000"

  var mapDiv = document.getElementById("boardMap")

  $scope.colorList = [ //
  colorNameToHex('brown'),//
  colorNameToHex('black'),//
  colorNameToHex('purple'),//
  colorNameToHex('blue'),//
  colorNameToHex('green'),//
  colorNameToHex('orange'),//
  colorNameToHex('red'),//
  colorNameToHex('darkblue') ];

  $scope.fillOpacity = 0.4
  $scope.strokeOpacity = 0.8

  $scope.boardMap = {}
  $scope.continents = []
  $scope.territories = []
  $scope.edgeMap = {}
  $scope.selectedContinent = {}
  $scope.selectedTerritory = {}

  $scope.currentBaseColor = $scope.colorList[0]
  $scope.selectedShape = null;

  $scope.newShapeNextId = 0;

  $scope.lastLatitude = 32.7150
  $scope.lastLongitude = 117.1625
  $scope.lastZoom = 4
  $scope.state = VIEW_MAP

  /*
   * Initialize Map
   */
  $scope.mapOptions = {
    zoom : $scope.lastZoom,
    maxZoom : 4,
    disableDefaultUI : true,
    center : new google.maps.LatLng($scope.lastLatitude, $scope.lastLongitude)
  }

  $scope.map = new google.maps.Map(mapDiv, $scope.mapOptions);

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

  /*
   * Shape Listeners
   */
  $scope.$watch('selectedShape', function(newVal, oldVal) {
    if (newVal != null && newVal.hasOwnProperty('type')) {
      newVal.setEditable(true);
      $scope.drawingManager.setOptions({
        drawingMode : null
      });
    } else {
      $scope.drawingManager.setOptions({
        drawingMode : google.maps.drawing.OverlayType.POLYGON
      });
    }
    if (oldVal != null && oldVal.hasOwnProperty('type')) {
      oldVal.setEditable(false);
    }
  });

  $scope.selectionDelete = function selectionDelete() {
    if ($scope.selectedShape != null && $scope.selectedShape != undefined && $scope.selectedShape.hasOwnProperty('type')) {
      $scope.selectedShape.setEditable(false);
      $scope.selectedShape.setMap(null);
      $scope.selectedShape = null
    }
  }

  $scope.onShapeEdited = function(shape) {
    console.log(shape.appId + ": shape edited");
  }

  $scope.onShapeClicked = function(shape) {
    $scope.selectedShape = shape
    $scope.$apply()
  }

  $scope.onDrawingModeChanged = function() {
    printDrawingMode($scope.drawingManager);
    $scope.selectedShape = {}
    $scope.$apply()
  }

  /*
   * New Shape
   */
  $scope.newShapeAddPathListeners = function(shape, path) {
    google.maps.event.addListener(path, 'insert_at', function() {
      $scope.onShapeEdited(shape)
    });
    google.maps.event.addListener(path, 'remove_at', function() {
      $scope.onShapeEdited(shape)
    });
    google.maps.event.addListener(path, 'set_at', function() {
      $scope.onShapeEdited(shape)
    });
  }

  $scope.newShapeAddListeners = function(shape) {
    google.maps.event.addListener(shape, 'click', function() {
      $scope.onShapeClicked(shape);
    });

    switch (shape.type) {
    case POLYGON:
      var paths = shape.getPaths();

      var n = paths.getLength();
      for (var i = 0; i < n; i++) {
        var path = paths.getAt(i);
        $scope.newShapeAddPathListeners(shape, path);
      }
      break;
    }
  }

  $scope.onNewShape = function(event) {
    $scope.drawingManager.setOptions({
      drawingMode : null
    });
    $scope.selectedShape = event.overlay;

    $scope.selectedShape.type = event.type;
    $scope.selectedShape.appId = $scope.newShapeNextId++;

    console.log("new shape created (id = " + $scope.selectedShape.appId + ")");

    $scope.newShapeAddListeners($scope.selectedShape);
    $scope.$apply()
  }

  /*map
   * Initialize Map Event Listeners
   */
  google.maps.event.addListener($scope.drawingManager, 'overlaycomplete', $scope.onNewShape);
  // google.maps.event.addListener($scope.drawingManager, 'overlaycomplete', $scope.onNewShape);

  google.maps.event.addListener($scope.map, 'bounds_changed', function(event) {
    $scope.updateLastFromMap()
     $scope.$apply()
  });

  google.maps.event.addListener($scope.map, 'click', function(mouseEvent) {
    $scope.lastLatitude = mouseEvent.latLng.lat()
    $scope.lastLongitude = mouseEvent.latLng.lng()
    $scope.selectedShape = null
    $scope.$apply()
  });

  /*
   * Initialize Drawing Event Listeners
   */
  $scope.$watch('selectedTerritory', function(newVal, oldVal) {
    if (oldVal.polygon != null) {
      oldVal.polygon.setMap(null)
    }
    if (newVal.hasOwnProperty('name')) {
      $scope.drawingManager.setMap($scope.map)
      var buttons = $('#shapecontrols .button')
      buttons.removeClass('disabled')
      if (!(newVal.polygon == null)) {
        newValue.polygon.setMap($scope.map)
      }
    } else {
      var buttons = $('#shapecontrols .button')
      buttons.addClass('disabled')
      $scope.drawingManager.setMap(null)
    }
    if (newVal.hasOwnProperty('geoLocations')) {
      $scope.$emit('fitBounds', [ newVal ])
      console.log('emitting \'fitBounds\'')
    }
  });

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
    $scope.updateLast($scope.map.getCenter().lat(), $scope.map.getCenter().lng(), $scope.map.getZoom())
  }

  $scope.updateLast = function(lat, lng, zoom) {
//    console.log("updateLast lat:" + lat + ", lng:" + lng + ", z:" + zoom)
    $scope.lastLatitude = lat
    $scope.lastLongitude = lng
    $scope.lastZoom = zoom
  }

  $scope.fitBounds = function(territoryArray) {
    var bounds = MapUtils.convertTerritoriesToBounds(territoryArray)
    $scope.map.fitBounds(bounds);
    $scope.updateLastFromMap()
  }

  /*
   * Map Utility Functions
   */
  $scope.$watch('selectedContinent', function(newValue, oldValue) {
    if (newValue.hasOwnProperty('territories')) {
      $scope.$emit('fitBounds', newValue.territories)
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
      $.each($scope.continents, function(index, continent) {
        continent.baseColor = $scope.colorList[index]
      });
      $scope.edgeMap = data.edgeMap
      $scope.territories = {}
      $scope.updateMapShapes()
    }).error(function(data, status, headers, config) {
      console.log('error in showMatch for id: ' + data.id);
    });
  }

  $scope.updateMapShapes = function() {
    $.each($scope.continents, function(index, continent) {
      $.each(continent.territories, function(index, territory) {
        var coordinates = []
        if (territory.geoLocations.length > 2) {
          $.each(territory.geoLocations, function(index, location) {
            coordinates.push(new google.maps.LatLng(location.latitude, location.longitude))
          });
          var polygon = new google.maps.Polygon({
            paths : coordinates,
            strokeColor : continent.baseColor,
            strokeOpacity : $scope.strokeOpacity,
            strokeWeight : 2,
            fillColor : continent.baseColor,
            fillOpacity : $scope.fillOpacity
          });
          polygon.territory = territory
          polygon.setMap($scope.map);
        }
      });
    });
  }

  $scope.addTerritoryPolygonToMap = function() {
    console.log('addTerritoryPolygon called')

    $.each($scope.shapeCoordinates, function(index, value) {
      coordinates.push(new google.maps.LatLng(value.latitude, value.longitude))
    });

    // Define a rectangle and set its editable property to true.
  }

  $scope.deleteShape = function() {
    console.log("delete button clicked");
    $scope.selectionDelete();
  }

  $scope.setShapeOnSelectedTerritory = function() {
    console.log("setShapeOnSelectedTerritory")
    if (!($scope.selectedShape == null || $scope.selectedShape == {})) {
      $.each($scope.selectedShape.getPath().getArray(), function(index, value) {
        var lat = value.lat();
        var lng = value.lng();
        console.log("new GeoLocation(latitude:" + lat + ",longitude:" + lng + "),")
      });
    }
  }
  
  $scope.refresh()

}
