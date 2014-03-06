
/**
 * Original Dynamix Demo Ctrls
 */
function TypeAheadCtrl($scope, $http) {

	$scope.url = undefined;
	$scope.fieldName = undefined;
	$scope.property = undefined;
	$scope.httpFilterObject = undefined;

	$scope.init = function(url, fieldName, property) {
		console.log("Initializing TypeAhead with url:" + url + ", fieldName:"
				+ fieldName + ", property:" + property);
		$scope.url = url;
		$scope.fieldName = fieldName;
		$scope.property = property;
		$scope.httpFilterObject = {
			params : {}
		};
		$scope.httpFilterObject.params[$scope.fieldName] = '';
		console
				.log("Set up filter object:"
						+ toString($scope.httpFilterObject));
	};

	$scope.getLocation = function(val) {
		console.log("Calling getLocation called at url:" + $scope.url
				+ " with val:" + val);
		$scope.httpFilterObject.params[$scope.fieldName] = val;
		console.log("Calling http get with filter object:"
				+ toString($scope.httpFilterObject));
		return $http.get($scope.url, $scope.httpFilterObject).then(
				function(res) {
					console.log("Result from request to url:" + $scope.url
							+ " with val:" + val + ", res:" + toString(res)
							+ ", res.data:" + toString(res.data));
					var models = [];
					angular.forEach(res.data[$scope.property], function(item) {
//						console.log("Pushing item:" + toString(item));
						models.push(item[$scope.fieldName]);
					});
					return models;
				});
	};
}

function GridCtrl($scope, $rootScope, $http) {

	$scope.staticColumnDefs = [ {
		displayName : 'Details',
		cellTemplate : '<div  ng-controller="ModalCtrl" ng-init="init(false,\'/\'+objectName+\'/show/\',row)" class="ngCellText" ng-class="col.colIndex()" style="text-align:center"><button ng-click="open()" style="padding:0; border:0"><img src="/images/app/default/action/details.png"/></button></div>'
	} ];
	$scope.hasSelection = false;
	$scope.currentSelection;
	$scope.currentSelections = [];
	$scope.filterOptions = {
		filterText : "",
		useExternalFilter : true
	};
	$scope.objectName = '';
	$scope.setObjectName = function(objectName) {
		console.log('setObjectName called with:' + objectName);
		$scope.objectName = objectName;
	}
	$scope.totalServerItems = 0;
	$scope.pagingOptions = {
		pageSizes : [ 25, 50, 99, 100 ],
		pageSize : 25,
		currentPage : 1
	};
	$scope.setPagingData = function(data, page, pageSize) {
		console.log('Setting myData to a list of size:'
				+ data[$scope.objectName + "s"].length);
		$scope.myData = data[$scope.objectName + 's'];
		$scope.totalServerItems = data.totalCount;
		if (!$scope.$$phase) {
			// console.log('Calling scope.apply...');
			$scope.$apply();
		} else {
			// console.log('NOT Calling scope.apply...');
		}
	};
	$scope.getPagedDataAsync = function(pageSize, page, searchText) {
		setTimeout(function() {
			var data;
			// if (searchText) {
			// var ft = searchText.toLowerCase();
			// $http.get($scope.objectName+'/all').success(
			// function(largeLoad) {
			// data = largeLoad.filter(function(item) {
			// return JSON.stringify(item).toLowerCase()
			// .indexOf(ft) != -1;
			// });
			// $scope.setPagingData(data, page, pageSize);
			// });
			// } else {
			$http.get(
					$scope.objectName + '/all?max=' + pageSize + '&offset='
							+ ((page - 1) * pageSize)).success(
					function(largeLoad) {
						$scope.setPagingData(largeLoad, page, pageSize);
					});
			// }
		}, 100);
	};

	$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
			$scope.pagingOptions.currentPage);

	$scope.$watch('pagingOptions', function(newVal, oldVal) {
		if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
			$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
					$scope.pagingOptions.currentPage,
					$scope.filterOptions.filterText);
		}
	}, true);
	$scope.$watch('filterOptions', function(newVal, oldVal) {
		if (newVal !== oldVal) {
			$scope.getPagedDataAsync($scope.pagingOptions.pageSize,
					$scope.pagingOptions.currentPage,
					$scope.filterOptions.filterText);
		}
	}, true);
	$scope.$watch('currentSelections',
			function(newVal, oldVal) {
				if (newVal !== oldVal) {
					$scope.hasSelection = newVal != null && newVal.length > 0
					$scope.currentSelection = newVal != null
							&& newVal.length > 0 ? newVal[0] : null;
					// console.log("Current selection
					// now:"+JSON.stringify($scope.currentSelection));
				}
			}, true);

	// $scope.onGridDoubleClick = function(row){
	// var rowData = $scope.myData[row.rowIndex];
	// alert("Double Clicked " +rowData)
	// }

	$scope.gridOptions = {
		data : 'myData',
		enablePaging : true,
		multiSelect : false,
		showFooter : true,
		selectedItems : $scope.currentSelections,
		totalServerItems : 'totalServerItems',
		columnDefs : $scope.staticColumnDefs.concat($rootScope.columnDefs),
		pagingOptions : $scope.pagingOptions,
		filterOptions : $scope.filterOptions,
		rowTemplate : '<div ng-controller="ModalCtrl" ng-init="init(false,\'/\'+objectName+\'/show/\',row)" ng-dblclick="open()" ng-style="{ \'cursor\': row.cursor }" ng-repeat="col in renderedColumns" ng-class="col.colIndex()" class="ngCell {{col.cellClass}}"><div class="ngVerticalBar" ng-style="{height: rowHeight}" ng-class="{ ngVerticalBarVisible: !$last }">&nbsp;</div><div ng-cell></div></div>'
	};
}

function PaginationCtrl($scope, $rootScope) {
	$scope.init = function(totalItems, currentPage) {
		console.log("Initializing, totalItems:" + totalItems + ", currentPage:"
				+ currentPage);
		$scope.totalItems = totalItems;
		$scope.currentPage = currentPage ? currentPage : 1;
	};

	$scope.pageChanged = function(page) {
		console.log("Page changed, page:" + page + ", currentPage:"
				+ $scope.currentPage);
		$rootScope.urlParams = "?offset=" + ((page - 1) * 10) + "&currentPage="
				+ page;
		console.log("$rootScope.urlParams:" + $rootScope.urlParams);
	};
};

function ReportCtrl($scope, $rootScope) {
	$scope.init = function(currentReport) {
		console.log("Initializing currentReport:" + currentReport);
		$scope.currentReport = currentReport ? currentReport : 0;
	};

	$scope.reportChanged = function(report) {
		console.log("Report changed, report:" + report + ", currentReport:"
				+ $scope.currentReport);
		$rootScope.urlParams = "?currentReport=" + report;
		console.log("$rootScope.urlParams:" + $rootScope.urlParams);
	};
};

var ModalCtrl = function($scope, $modal, $log, $http) {
	$scope.init = function(submit, url, row) {
		if (row != null) {
			$scope.url = url + $scope.myData[row.rowIndex].id;
		} else {
			$scope.url = url;
		}

		if (url.indexOf("?") != -1) {
			$scope.url = $scope.url + "&submit=" + submit;
		} else {
			$scope.url = $scope.url + "?submit=" + submit;
		}

	}

	$scope.open = function() {
		var modalInstance = $modal.open({
			templateUrl : $scope.url,
			controller : ModalInstanceCtrl
		});
	};
};

var ModalInstanceCtrl = function($scope, $modalInstance, $http) {
	$scope.model = {};

	$scope.ok = function() {
		console.log("Closing b/c of ok");
		$modalInstance.close('ok');
	};
	$scope.cancel = function() {
		console.log("Closing b/c of cancel");
		$modalInstance.close('cancel');
	};
	$scope.submit = function(submitUrl) {
		console.log("Calling post to url:" + submitUrl + " with model:"
				+ $scope.model);
		$http.post(submitUrl, $scope.model).success(
				function(res) {
					console.log("Result from request to url:" + submitUrl
							+ " with model:" + $scope.model + ", res:"
							+ toString(res));
					$modalInstance.close('submit');
				}).error(
				function(res) {
					console.log("Error result from request to url:" + submitUrl
							+ " with model:" + $scope.model + ", res:"
							+ toString(res));
				});
	};
};