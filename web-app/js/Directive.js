/**
 * Imperil Directives
 */
imperilApp.directive('ngRepeatEmitter', function($timeout) {
  return {
    restrict : 'A',
    link : function(scope, element, attr) {
      $timeout(function() {
        scope.$emit('ngRepeatIterated', element);
      });
      if (scope.$last === true) {
        $timeout(function() {
          scope.$emit('ngRepeatFinished');
        });
      }
    }
  }
});

