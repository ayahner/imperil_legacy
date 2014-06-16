/**
 * Home Controller
 */
var HomeController = function($rootScope, $scope, $http, $routeParams, $log, NavService) {
  $scope.navTo = NavService.navTo
  $scope.currentUser = {}
  $scope.myMatches = []
  $scope.myMaps = []

  $scope.$on('ngRepeatIterated', function(ngRepeatIteratedEvent, element) {
    console.log('ngRepeatIterated: ' + ngRepeatIteratedEvent)
    var icon = element.children().find('.checkered.flag.icon')
    var currentState = icon.attr('currentState')

    var currentPlayerUserId = icon.attr('currentPlayerUserId')
    var currentUserId = icon.attr('currentUserId')

    if ([ "Complete" ].contains(currentState)) {
      icon.addClass('red')
    } else if ([ "Playing", "Choosing Territories" ].contains(currentState) && currentPlayerUserId == currentUserId) {
      icon.addClass('green')
    }
  });

  $scope.refreshMatches = function() {
    console.log('refreshMatches() called for "/"')
    $http.get('/match/listMine').success(function(data, status, headers, config) {
      console.log('success refreshMatches()');
      $scope.myMatches = data
    }).error(function(data, status, headers, config) {
      console.log('error in refreshMatches() for user');
    });
  }

  $scope.refreshMaps = function() {
    console.log('refreshMaps() called for "/"')
    $http.get('/boardMap/listMine').success(function(data, status, headers, config) {
      console.log('success refreshMaps()');
      $scope.myMaps = data
    }).error(function(data, status, headers, config) {
      console.log('error in refreshMaps()');
    });
  }

  $scope.deleteBoardMap = function(event, id) {
    console.log('deleteBoardMap() called for "/"')
    $http.delete('/boardMap/delete?id='+id).success(function(data, status, headers, config) {
      console.log('success deleteBoardMap()');
      $scope.refreshMaps();
    }).error(function(data, status, headers, config) {
      console.log('error in deleteBoardMap()');
    });
  }

  $scope.refresh = function() {
    console.log('refresh() called for "/"')
    $scope.refreshMatches();
    $scope.refreshMaps();
  }
  $scope.refresh()
}
