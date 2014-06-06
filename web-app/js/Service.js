/**
 * Imperil Services
 */
imperilApp.service('NavService', [ '$rootScope', '$location', function($rootScope, $location) {
  return {
    navTo : function(path) {
      $location.path(path);
    }
  };
} ]);

