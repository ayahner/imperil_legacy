function LoginCtrl($scope) {
	$scope.templates = [ {
		name : 'login form',
		url : 'loginForm.gsp'
	} ];
	$scope.template = $scope.templates[0];
}