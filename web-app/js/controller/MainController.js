/**
 * Main Controller 
 */
imperilApp.controller('MainController', function($rootScope, $scope, $route, $routeParams, $location, NavService) {
  $scope.$route = $route;
  $scope.$location = $location;
  $scope.$routeParams = $routeParams;
  $scope.navTo = NavService.navTo
  
  $scope.openMatch = function(event, id) {
    $location.url('/match?id=' + id)
  }

  $scope.openBoardMap = function(event, id) {
    $location.url('/boardMap?id=' + id)
  }

  $scope.openCreateMatch = function(event) {
    $scope.$emit('openCreateMatchModal', event)
  }

  $scope.openCreateBoardMap = function(event) {
    $scope.$emit('openCreateBoardMapModal', event)
  }

  
  $scope.toggleDebugView = function() {
    console.log('toggleDebugView()')
    $('.ui.left.sidebar').sidebar('toggle');
  }

});
