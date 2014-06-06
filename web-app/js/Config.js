/**
 * Imperil Configs
 */

// configure our routes
imperilApp.config(function($routeProvider, $locationProvider) {
  $routeProvider
  // route for the home page
  .when('/', {
    templateUrl : '/pages/home',
    controller : 'MainController'
  })
  // route for the match page
  .when('/match', {
    templateUrl : '/pages/match',
    controller : 'MainController'
  })
  // route for the match page
  .when('/settings', {
    templateUrl : '/pages/settings',
    controller : 'MainController'
  })
  // route for the match page
  .when('/tools', {
    templateUrl : '/pages/tools',
    controller : 'MainController'
  });
});
