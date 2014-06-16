/**
 * Create BoardMap Controller
 */
var CreateBoardMapController = function($rootScope, $scope, $http, $location, $routeParams, $log) {
  $scope.availableBoardMaps = [];
  $scope.selectedBoardMap = {};
  $scope.boardMap = {};
  $rootScope.$on('closeCreateBoardMapModal', function(event) {
    console.log('closeCreateBoardMapModal');
    $('.create.boardmap.modal').modal('hide');    
  });
  
  $rootScope.$on('openCreateBoardMapModal', function(event) {
    console.log('openCreateBoardMapModal');
    $scope.resetForm()
    $('.create.boardmap.modal').modal('setting', {
      closable : false,
      onApprove : function() {
        if ($('.create.boardMap.form').form('validate form')) {
          console.log('call createBoardMap ' + $scope.boardMap.name);
          $http.post('/boardMap/create', {boardMap:$scope.boardMap,oldBoardMapId:$scope.selectedBoardMap.id}).success(function(data, status, headers, config) {
            console.log('success in createBoardMap with id: ' + data.id);
            $location.path('/boardMap').search({
              id : data.id
            })
            $rootScope.$emit('closeCreateBoardMapModal')
          }).error(function(data, status, headers, config) {
            console.log('error in createBoardMap for id: ' + data.id);
          });
        }
        return false;
      }
    }).modal('show');
  });

  $scope.resetForm = function() {
    $scope.selectedBoardMap = {}
    $scope.boardMap = {}
    $scope.refreshBoardMaps()
    $('.create.boardmap.form').form(clearValidationRules);
    $('.create.boardmap.form').form('validate form');

    $('.create.boardmap.form').form(validationRules, {
      on : 'blur',
      inline : false
    });
  }

  $scope.refreshBoardMaps = function() {
    $http.get('/boardMap/list').success(function(data) {
      $scope.availableBoardMaps = data
    });
  }

  $scope.selectBoardMap = function(event, boardMap) {
    console.log('selecting boardMap: ' + boardMap.name)
    var currentTarget = $(event.currentTarget)
    currentTarget.closest('table').find('tr').removeClass('active')
    if ($scope.selectedBoardMap.id == boardMap.id) {
      $scope.selectedBoardMap = {}
    } else {
      $scope.selectedBoardMap = boardMap
      currentTarget.addClass('active')
    }
  }

  var validationRules = {
    name : {
      identifier : 'name',
      rules : [ {
        type : 'empty',
        prompt : 'Name is required'
      } ]
    },
    description : {
      identifier : 'description',
      rules : [ {
        type : 'empty',
        prompt : 'Description is required'
      } ]
    }
  }
  var clearValidationRules = {
    name : {
      identifier : 'name',
      rules : []
    },
    description : {
      identifier : 'description',
      rules : []
    }
  }
}
