
/**
 * Match Controller
 */
var MatchController = function($rootScope, $scope, $http, $routeParams, $log) {
  $scope.match = null
  $scope.currentPlayer = null
  $scope.players = []
  
  $scope.boardMap = null
  $scope.selectedContinent = null
  $scope.selectedTerritory = null

  console.log('requesting: /match/show');

  //emit the continents update event
  $scope.$watch('boardMap', function(newValue, oldValue) {
    $rootScope.$emit('updateMapView', newValue == null?null:newValue.continents)
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

  $scope.enableAddArmy = function(territory) {
    return $scope.match.isMyTurn// && territory.ownedByMe && $scope.match.state
    // == 'Choosing Territories' &&
    // territory.armyCount == 0)
  }

  $scope.refresh = function() {
    console.log('refresh called')
    $scope.updateMatch($routeParams.id)
  }

  $scope.selectContinent = function(event, continent) {
    var currentTarget = $(event.currentTarget)
    currentTarget.closest('table').find('tr').removeClass('active')
    currentTarget.addClass('active')
    console.log('selecting continent: ' + continent.name)
    $scope.selectedContinent = continent
  }

  $scope.selectTerritory = function(event, territory) {
    var currentTarget = $(event.currentTarget)
    currentTarget.closest('table').find('tr').removeClass('active')
    currentTarget.addClass('active')
    console.log('selecting territory: ' + territory.name)
    $scope.selectedTerritory = territory
  }

  $scope.updateMatch = function(id) {
    console.log('refresh called')
    $http.get('/match/show', {
      params : {
        id : id
      }
    }).success(function(data, status, headers, config) {
      console.log('success: match: ' + data.name + ' for map: ' + data.boardMap.name);
      $scope.match = data
      $scope.currentPlayer = data.currentPlayer
      $scope.players = data.players
      $scope.boardMap = data.boardMap
    }).error(function(data, status, headers, config) {
      console.log('error in showMatch for id: ' + data.id);
    });
  }

  $scope.addArmy = function(territory, count) {
    console.log('adding ' + count + ' armies to ' + territory.name + ' for player: ' + $scope.currentPlayer.name);
    $http.post('/match/addArmies', {
      params : {
        match : $scope.match,
        territory : territory,
        count : count
      }
    }).success(function(data, status, headers, config) {
      $scope.refresh()
    }).error(function(data, status, headers, config) {
      console.log('error in addArmies for id: ' + data.id);
    });
  };

  $scope.refresh();
}
