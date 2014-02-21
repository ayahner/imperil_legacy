var app = angular.module('main', [ 'ngAnimate', 'ui.bootstrap','ngGrid' ]);

app.directive('focusMe', function($timeout, $parse) {
	  return {
	    //scope: true,   // optionally create a child scope
	    link: function(scope, element, attrs) {
	      var model = $parse(attrs.focusMe);
	      scope.$watch(model, function(value) {
	        //console.log('value=',value);
	        if(value === true) { 
	          $timeout(function() {
	            element[0].focus(); 
	          });
	        }
	      });
	    }
	  };
	});

function toString(obj) {
	var str = '';
	for ( var p in obj) {
		if (obj.hasOwnProperty(p)) {
			str += p + '::' + obj[p] + '\n';
		}
	}
	return str;
}