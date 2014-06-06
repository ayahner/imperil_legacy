<div ng-controller="MainController">
  <div class="ui menu">
    <a class="item" href="/">Main Menu</a>
    <sec:ifLoggedIn>
      <sec:ifAnyGranted roles="play,mod,admin">
        <a class="item" href="" ng-click="openCreateMatch()">Create Match</a>
        <a class="item" ng-click="navTo('/settings')">Settings</a>
      </sec:ifAnyGranted>
      <sec:ifAnyGranted roles="mod,admin">
        <a class="item" ng-click="navTo('/tools')">Tools</a>
      </sec:ifAnyGranted>
      <div class="right menu">
        <sec:ifAnyGranted roles="play,mod,admin">
          <a class="item" href="" ng-click="toggleDebugView()">Debug</a>
        </sec:ifAnyGranted>
        <a class="item" href="/j_spring_security_logout">Logout <sec:loggedInUserInfo field="username" /></a>
      </div>
    </sec:ifLoggedIn>
  </div>
  <div class="ui left sidebar">
    <div class="ui compact list">
      <div class="item">$location.path() = {{$location.path()}}</div>
      <div class="item">$route.current.templateUrl = {{$route.current.templateUrl}}</div>
      <div class="item">$route.current.params = {{$route.current.params}}</div>
      <div class="item">$route.current.scope.name = {{$route.current.scope.name}}</div>
      <div class="item">$routeParams = {{$routeParams}}</div>
    </div>
  </div>
</div>
<div class="ui create small match modal" ng-controller="CreateMatchController">
  <i class="close icon"></i>
  <div class="header">Create Match</div>
  <div class="content">
    <div class="ui create match form">
      <div class="field">
        <label>Name</label> <input name="name" type="text" placeholder="Match Name" ng-model="match.name">
      </div>
      <div class="field">
        <label>Description</label> <input name="description" type="text" placeholder="Match Description" ng-model="match.description">
      </div>
      <table class="ui small compact table segment">
        <thead>
          <tr>
            <th>User</th>
            <th>Name</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          <tr ng-repeat="player in availablePlayers" ng-click="selectPlayer($event, player)">
            <td>{{player.user.username}}</td>
            <td>{{player.name}}</td>
            <td>{{player.description}}</td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <th>{{availablePlayers.length}} Player{{allAvalablePlayers.length==1?"":"s"}}</th>
            <th>{{selectedPlayers.length}} Selected</th>
            <th></th>
          </tr>
        </tfoot>
      </table>
      <div class="actions">
        <div class="ui black close button">Cancel</div>
        <div class="ui positive right labeled icon button">
          Create Match <i class="checkmark icon"></i>
        </div>
      </div>
    </div>
  </div>
</div>
