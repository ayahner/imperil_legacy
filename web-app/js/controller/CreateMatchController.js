/**
 * Create Match Controller
 */
var CreateMatchController = function($rootScope, $scope, $http, $location, $routeParams, $log) {
  $scope.availablePlayers = [];
  $scope.selectedPlayers = [];
  $scope.availableBoardMaps = [];
  $scope.selectedBoardMap = {};
  $scope.match = {};
  
  $rootScope.$on('closeCreateMatchModal', function(event) {
    console.log('closeCreateMatchModal');
    $('.create.match.modal').modal('hide');    
  });
  
  $rootScope.$on('openCreateMatchModal', function(event) {
    console.log('openCreateMatchModal');
    $scope.resetForm()
    $('.create.match.modal').modal('setting', {
      closable : false,
      onApprove : function() {
        if ($('.create.match.form').form('validate form')) {
          console.log('call createMatch ' + $scope.match.name);
          $scope.match.players = $scope.selectedPlayers
          $http.post('/match/save', $scope.match).success(function(data, status, headers, config) {
            console.log('success in createMatch with id: ' + data.id);
            $location.path('/match').search({
              id : data.id
            })
            $rootScope.$emit('closeCreateMatchModal')
          }).error(function(data, status, headers, config) {
            console.log('error in createMatch for id: ' + data.id);
          });
        }
        return false;
      }
    }).modal('show');
  });

  $scope.resetForm = function() {
    $scope.selectedPlayers = []
    $scope.selectedBoardMap = {}
    $scope.match = {}
    $scope.refreshPlayers()
    $scope.refreshBoardMaps()
    $('.create.match.form').form(clearValidationRules);
    $('.create.match.form').form('validate form');

    $('.create.match.form').form(validationRules, {
      on : 'blur',
      inline : false
    });
  }

  $scope.refreshPlayers = function() {
    $http.get('/playerPreferences/list').success(function(data) {
      $scope.availablePlayers = data
    });
  }
  $scope.refreshBoardMaps = function() {
    $http.get('/boardMap/list').success(function(data) {
      $scope.availableBoardMaps = data
    });
  }

  $scope.selectPlayer = function(event, player) {
    var currentTarget = event.currentTarget
    for (var i = 0; i < $scope.selectedPlayers.length; i++) {
      if ($scope.selectedPlayers[i].id == player.id) {
        $(currentTarget).removeClass('active')
        $scope.selectedPlayers.splice(i)
        console.log('removed player: ' + player.name)
        return;
      }
    }
    $(currentTarget).addClass('active')
    $scope.selectedPlayers.push(player)

    console.log('selecting player: ' + player.name)
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
