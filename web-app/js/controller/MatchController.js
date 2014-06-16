
/**
 * Match Controller
 */
var MatchController = function($rootScope, $scope, $http, $routeParams, $log) {
  $scope.match = {}
  $scope.currentPlayer = {}
  $scope.currentUser = {}
  $scope.players = []
  $scope.boardMap = {}
  $scope.continents = []
  $scope.territories = []
  $scope.edgeMap = {}
  $scope.selectedContinent = {}
  $scope.selectedTerritory = {}

  console.log('requesting: /match/show');

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
    $scope.territories = continent.territories
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
      $scope.continents = data.boardMap.continents
      $scope.territories = data.boardMap.territories
      $scope.edgeMap = data.boardMap.edgeMap
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
