// configure our routes
app.config(function($routeProvider) {
	$routeProvider
	// route for the home page
	.when('/', {
		templateUrl : '/pages/home',
		controller : 'mainController'
	})
	// route for the match page
	.when('/match', {
		templateUrl : '/pages/match',
		controller : 'mainController'
	});
});

// create the controller and inject Angular's $scope
app.controller('mainController', function($scope, $location) {
	$scope.go = function(path) {
		$location.path(path);
	};
});

app.controller('matchController', function($scope, $location) {
});

var ModalCreateMatchCtrl = function($scope, $modal, $log) {

	$scope.items = [ 'item1', 'item2', 'item3' ];

	$scope.open = function() {

		var modalInstance = $modal.open({
			templateUrl : 'myModalContent.html',
			controller : ModalCreateMatchInstanceCtrl,
			resolve : {
				items : function() {
					return $scope.items;
				}
			}
		});

		modalInstance.result.then(function(selectedItem) {
			$scope.selected = selectedItem;
		}, function() {
			$log.info('Modal dismissed at: ' + new Date());
		});
	};
};

// Please note that $modalInstance represents a modal window (instance)
// dependency.
// It is not the same as the $modal service used above.

var ModalCreateMatchInstanceCtrl = function($scope, $modalInstance, items) {

	$scope.items = items;
	$scope.selected = {
		item : $scope.items[0]
	};

	$scope.ok = function() {
		$modalInstance.close($scope.selected.item);
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
};
