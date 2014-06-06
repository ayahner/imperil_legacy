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
	})
	// route for the match page
	.when('/settings', {
		templateUrl : '/pages/settings',
		controller : 'mainController'
	})
	// route for the match page
	.when('/tools', {
		templateUrl : '/pages/tools',
		controller : 'mainController'
	});
}).service( 'NavService', [ '$rootScope', '$location', function( $rootScope, $location ) {
	return {
		navTo: function( path ) {
			$location.path(path);
		} 
	};
}]);

// create the controller and inject Angular's $scope
app.controller('mainController', function($scope, NavService) {
	$scope.navTo = NavService.navTo
});

var ModalMatchCtrl = function($rootScope, $scope, $modal, $log) {

	$scope.createMatch = function() {
		var modalInstance = $modal.open({
			templateUrl : 'createMatchContent.html',
			controller : ModalCreateMatchInstanceCtrl
		});

		modalInstance.result.then(function(selectedItem) {
			
		}, function() {
			$log.info('Create Match Modal dismissed at: ' + new Date());
		});
	};

	$scope.resumeMatch = function() {

		var modalInstance = $modal.open({
			templateUrl : 'resumeMatchContent.html',
			controller : ModalResumeMatchInstanceCtrl
		});

		modalInstance.result.then(function(selectedItem) {
			
		}, function() {
			$log.info('Resume Match Modal dismissed at: ' + new Date());
		});
	};

	$scope.settings = function() {

		var modalInstance = $modal.open({
			templateUrl : 'settingsContent.html',
			controller : ModalSettingsInstanceCtrl
		});

		modalInstance.result.then(function(selectedItem) {
			
		}, function() {
			$log.info('Settings Modal dismissed at: ' + new Date());
		});
	};

	$scope.tools = function() {

		var modalInstance = $modal.open({
			templateUrl : 'toolsContent.html',
			controller : ModalToolsInstanceCtrl
		});

		modalInstance.result.then(function(selectedItem) {
			
		}, function() {
			$log.info('Tools Modal dismissed at: ' + new Date());
		});
	};
};

/**
 * Modal Instance Controllers begin
 */
var ModalCreateMatchInstanceCtrl = function($location, $http, $scope, $rootScope, $modalInstance, $log) {
		
	$scope.selectedPlayers = [];
	$scope.match = {}
	
	$scope.createMatchPlayersGridData = $http.get('/playerPreferences/list').success(function (data) {
		$scope.createMatchPlayersGridData = data
	});
	
    $scope.createMatchPlayersGridOptions = { 
    		data: 'createMatchPlayersGridData',
    		columnDefs: [{field:'name', displayName:'Name'}, {field:'description', displayName:'Description'}],
    		selectedItems: $scope.selectedPlayers,
    		multiSelect: true
	};

	$scope.ok = function() {
		console.log('call createMatch '+$scope.match.name);
		$scope.match.players = $scope.selectedPlayers
	    $http.post('/match/save', $scope.match)
	    .success(function(data, status, headers, config) {
			console.log('success in createMatch with id: '+data.id);
	    	$location.path('/match').search({id: data.id})
	     }).error(function(data, status, headers, config) {
	 		console.log('error in createMatch for id: '+data.id);
	    });
		$modalInstance.close();
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
};

var ModalResumeMatchInstanceCtrl = function($location, $http, $scope, $modalInstance, $log, NavService) {
	$scope.navTo = NavService.navTo
	$scope.selectedMatches = [];
	$scope.resumeMatchGridData = $http.get('/match/listMine').success(function (data) {
		$scope.resumeMatchGridData = data
	});
	
    $scope.resumeMatchGridOptions = { 
    		data: 'resumeMatchGridData',
    		columnDefs: [{field:'name', displayName:'Name'}, {field:'description', displayName:'Description'}],
    		selectedItems: $scope.selectedMatches,
    		multiSelect: false
	};
	
    $scope.ok = function() {
    	$log.debug($scope.selected)
		$modalInstance.close();
    	var id = $scope.selectedMatches[0].id
    	$location.path('/match').search({id: id})
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
};
var ModalSettingsInstanceCtrl = function($http, $scope, $modalInstance, $log, NavService) {
	$scope.navTo = NavService.navTo

	$scope.ok = function() {
		$modalInstance.close();
		$scope.navTo('/settings')
	};

	$scope.cancel = function() {
//		$log.debug('Cancel clicked')
		$modalInstance.dismiss('cancel');
	};
};
var ModalToolsInstanceCtrl = function($http, $scope, $modalInstance, $log, NavService) {
	$scope.navTo = NavService.navTo

	$scope.ok = function() {
		$modalInstance.close();
		$scope.navTo('/tools')
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
};
/**
 * Modal Instance Controllers end
 */

var MatchGameController = function($rootScope, $scope, $http, $routeParams, $log) {
	$log.debug("routeParams: "+$routeParams.id)
	$scope.match = {}
	$scope.currentPlayer = {}
	$scope.players = []
	$scope.boardMap = {}
	$scope.continents = []
	$scope.territories = []
	$scope.edgeMap={}
	$scope.selectedTerritory= {}

	console.log('requesting: /match/show');
		
	$scope.refresh = function() {
		console.log('refresh called')
		$http.get('/match/show', {params:{id:$routeParams.id}}).success(function (data, status, headers, config) {
			console.log('success: match: '+data.name+' for map: '+data.boardMap.name);
	 		$scope.match = data
	 		$scope.currentPlayer = data.currentPlayer
	 		$scope.players = data.players
	 		$scope.boardMap = data.boardMap
	 		$scope.continents = data.boardMap.continents
	 		$scope.territories = data.boardMap.territories
	 		$scope.edgeMap=data.boardMap.edgeMap
		}).error(function(data, status, headers, config) {
	 		console.log('error in showMatch for id: '+data.id);
	    });		
	}
	
	$scope.addArmy = function(match, player, territory, count) {
 		console.log('adding '+count+' armies to '+territory.name+' for player: '+player.name);
 		$http.post('/match/addArmies', {params:{match:match, player:player, territory:territory, count:count}}).success(function (data, status, headers, config) {
 			console.log('success: added: '+data.name+' for map: '+data.boardMap.name);
 			refresh()
 		}).error(function(data, status, headers, config) {
 	 		console.log('error in addArmies for id: '+data.id);
 	    });
	};

	$scope.refresh();
}
