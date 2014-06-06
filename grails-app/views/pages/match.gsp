<div class="ui basic segment" ng-controller="MatchController">
  <div class="ui two column grid">
    <div class="column">
      <div class="ui blue segment">
        <div class="ui blue ribbon label" ng-click="showMatchDetails = !showMatchDetails">Match: {{match.name}}</div>
        <div class="ui description">{{match.description}}</div>
        <div class="ui hidable compact list" ng-class="{ 'hidden': showMatchDetails }">
          <div class="item">
            <table class="ui small compact basic table">
              <tbody>
                <tr>
                  <td class="three wide">Status</td>
                  <td class="seven wide">{{match.state}}</td>
                </tr>
                <tr>
                  <td class="three wide">Players</td>
                  <td class="seven wide"><span ng-repeat='player in match.players'>{{player.name}}<span class="label">{{player.armyCount}}</span> <span
                      ng-show=" ! $last ">, </span>
                  </span></td>
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

      <div class="ui red segment">
        <div class="ui red ribbon label" ng-click="showBoardDetails = !showBoardDetails">Map: {{boardMap.name}}</div>
        <div class="description">{{boardMap.description}}</div>
        <div class="ui large hidable compact list" ng-class="{ 'hidden': showBoardDetails }">
          <div class="item">
            <table class="ui small compact basic table">
              <tbody>
                <tr>
                  <td class="three wide">Continents</td>
                  <td class="seven wide">{{continents.length}}</td>
                </tr>
                <tr>
                  <td class="three wide">Territories</td>
                  <td class="seven wide">{{edgeMap.territoryCount}}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="item">
            <div class="ui small header" ng-click="showTerritoryDetails = !showTerritoryDetails">Details</div>
          </div>
          <div class="ui large hidable compact list" ng-class="{ 'hidden': !showTerritoryDetails }">
            <div ng-repeat="continent in continents">
              <div class="ui item">
                <div class="ui tiny header">{{continent.name}}</div>
                <table class="ui small compact basic table">
                  <tbody>
                    <tr ng-repeat="territory in continent.territories">
                      <td class="four wide">{{territory.name}}</td>
                      <td class="three wide">{{territory.garrison.owner.name}}</td>
                      <td class="three wide">{{territory.armyCount}} armies</td>
                      <td>
                        <div class="ui icon army button" ng-show="enableAddArmy(territory)" ng-click="addArmy(territory, 1)">
                          <i class="user icon"></i>
                        </div>
                      </td>
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
      <div class="ui green segment">
        <div class="ui green ribbon label" ng-click="showMatchDetails = !showMatchDetails">Game Map</div>
        The game map should be here
      </div>
    </div>
  </div>
</div>
