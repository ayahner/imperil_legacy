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
  $scope.selectedTerritory = {}

  var mapOptions = {
    center : new google.maps.LatLng(-34.397, 150.644),
    zoom : 8
  };

  $scope.map = new google.maps.Map(document.getElementById("matchMap"), mapOptions);

  // function initialize() {
  // }
  // google.maps.event.addDomListener(window, 'load', initialize);

  console.log('requesting: /match/show');

  $scope.enableAddArmy = function(territory) {
    return $scope.match.isMyTurn// && territory.ownedByMe && $scope.match.state
    // == 'Choosing Territories' &&
    // territory.armyCount == 0)
  }

  $scope.refresh = function() {
    console.log('refresh called')
    $http.get('/match/show', {
      params : {
        id : $routeParams.id
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
      // console.log('success: added: ' + data.name + ' for map: ' +
      // data.boardMap.name);
      $scope.refresh()
    }).error(function(data, status, headers, config) {
      console.log('error in addArmies for id: ' + data.id);
    });
  };

  $scope.refresh();
}
