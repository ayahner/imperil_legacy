<div class="ui basic segment">
  <div class="ui two column stackable grid" ng-controller="HomeController">
    <div class="column">
      <div class="ui blue segment">
        <div class="ui blue ribbon label">My Matches</div>
        <div class="ui compact list">
          <div class="item" ng-repeat='match in myMatches'>
            <div id="match-{{match.id}}" class="ui right floated tiny green play button" ng-click="openMatch($event)">Open</div>
            <div class="header" ng-click="showDetails = !showDetails">
              <i class="checkered flag {{match.state=='Complete'?'red ':match.isMyTurn?'green ':''}}icon"></i>{{match.name}}
            </div>
            <div class="ui hidable compact list" ng-class="{ 'hidden': !showDetails }">
              <div class="item">
                <table class="ui small compact basic table">
                  <tbody>
                    <tr>
                      <td class="three wide">Map</td>
                      <td class="seven wide">{{match.boardMap.name}}</td>
                    </tr>
                    <tr>
                      <td class="three wide">Status</td>
                      <td class="seven wide">{{match.state}}</td>
                    </tr>
                    <tr>
                      <td class="three wide">Players</td>
                      <td class="seven wide"><span ng-repeat='player in match.players'>{{player.name}}<span ng-show=" ! $last ">, </span></span></td>
                    </tr>
                    <tr>
                      <td class="three wide">Current Player</td>
                      <td class="seven wide">{{match.currentPlayer.name}}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="column">
      <div class="ui orange segment">
        <div class="ui orange ribbon label">My Maps</div>
        <div class="ui compact list">
          <div class="item" ng-repeat='map in myMaps'>
            <div class="content">
              <div class="header">
                <i class="globe icon"></i>{{map.name}}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
