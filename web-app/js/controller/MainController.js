/**
 * Main Controller 
 */
imperilApp.controller('MainController', function($rootScope, $scope, $route, $routeParams, $location, NavService) {
  $scope.$route = $route;
  $scope.$location = $location;
  $scope.$routeParams = $routeParams;
  $scope.navTo = NavService.navTo
  
  $scope.openMatch = function(event) {
    var id = event.currentTarget.id.substring(6)
    $location.url('/match?id=' + id)
  }

  $scope.openCreateMatch = function(event) {
    $rootScope.$emit('openCreateMatchModal', event)
  }

  
  $scope.toggleDebugView = function() {
    console.log('toggleDebugView()')
    $('.ui.left.sidebar').sidebar('toggle');
  }

});
