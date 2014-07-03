/**
 * Edit BoardMap Controller
 */
var EditBoardMapController = function($rootScope, $scope, $http, $routeParams, $log) {
  $scope.selectedOverlayPendingSave = false

  $scope.boardMap = null
  $scope.selectedContinent = null
  $scope.selectedTerritory = null

  $scope.selectedOverlay = null

  $scope.$watch('boardMap', function(newValue, oldValue) {
    $rootScope.$emit('updateMapView', newValue == null ? null : newValue.continents)
  }, true);

  $scope.$watch('selectedContinent', function(newValue, oldValue) {
    $scope.selectedTerritory = null;
    if (newValue != null && newValue.hasOwnProperty('territories')) {
      $scope.$emit('fitBounds', newValue.territories)
      console.log('emitting \'fitBounds\'')
    }
  }, true);

  $scope.$watch('selectedTerritory', function(newValue, oldValue) {
    $rootScope.$emit('territorySelected', newValue)
    if (newValue != null && newValue.hasOwnProperty('geoLocations')) {
      $scope.$emit('fitBounds', [ newValue ])
      console.log('emitting \'fitBounds\'')
    }
  }, true);

  $rootScope.$on('overlayClicked', function(event, overlay) {
    $scope.selectedContinent = overlay.continent
    $scope.$apply()
    $scope.selectedTerritory = overlay.territory
    $scope.$apply()
  })
  
  $rootScope.$on('overlayEdited', function(event, overlay) {
    console.log(overlay.appId + ": overlay edited");
    $scope.selectedOverlayPendingSave = true
    $scope.$apply()
  })
  
  $rootScope.$on('overlaySelected', function(event, overlay) {
    console.log('Map view received overlaySelected event')
    $scope.selectedOverlay = overlay
  })

  $scope.selectContinent = function(event, continent) {
    console.log('selecting continent: ' + continent.name)
    $scope.selectedContinent = continent
  }

  $scope.selectTerritory = function(event, territory) {
    console.log('selecting territory: ' + territory.name)
    $scope.selectedTerritory = territory
  }

  /*
   * Map Utility Functions
   */
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
    }).error(function(data, status, headers, config) {
      console.log('error in showMatch for id: ' + data.id);
    });
  }

  /*
   * Map button handlers
   */
  $scope.addOverlay = function() {
    if (!$scope.showAddButton()) return
    if ($scope.selectedOverlay != null) {
      $scope.clearSelectedOverlay()
      $scope.saveOverlayChanges()
    }
    $rootScope.$emit('enableDrawingManager')
  }

  $scope.removeOverlay = function() {
    if (!$scope.showRemoveButton()) return
    if ($scope.selectedOverlay != null) {
      $scope.clearSelectedOverlay()
      $scope.saveOverlayChanges()
    }
    $rootScope.$emit('disableDrawingManager')
  }

  $scope.editOverlay = function() {
    if (!$scope.showEditButton()) return
    if ($scope.selectedOverlay != null) {
      $scope.selectedOverlay.setEditable(true)
    }
    $rootScope.$emit('disableDrawingManager')
  }

  $scope.saveOverlay = function() {
    if (!$scope.showSaveButton()) return
    $scope.saveOverlayChanges()
    $rootScope.$emit('disableDrawingManager')
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
      $rootScope.$emit('overlayRemoved', $scope.selectedOverlay)
      $scope.selectedOverlay = null
      $scope.selectedOverlayPendingSave=false
    }
  }

  /*
   * Initialize Map and Map Event Listeners
   */
  $scope.showAddButton = function() {
    return $scope.selectedTerritory != null && $scope.selectedOverlay == null && !$scope.selectedOverlayPendingSave
  }
  $scope.showRemoveButton = function() {
    return $scope.selectedTerritory != null && $scope.selectedOverlay != null
  }
  $scope.showEditButton = function() {
    return $scope.selectedTerritory != null && $scope.selectedOverlay != null && !$scope.selectedOverlayPendingSave
  }
  $scope.showSaveButton = function() {
    return $scope.selectedTerritory != null && $scope.selectedOverlay != null && $scope.selectedOverlayPendingSave
  }
  /*
   * Refresh the page
   */

  $scope.refresh()
}
