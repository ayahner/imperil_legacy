<div ng-controller="MatchController">
	<div class="ui basic segment">
		<div class="ui two column stackable grid">
			<div class="six wide column">
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
										<td class="seven wide"><span ng-repeat='player in match.players'>{{player.name}}<span class="label">{{player.armyCount}}</span> <span ng-show=" ! $last ">,
											</span>
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
										<td class="three wide">Continent</td>
										<td class="seven wide">{{selectedContinent.name}}</td>
									</tr>
									<tr>
										<td class="three wide">Territory</td>
										<td class="seven wide">{{selectedTerritory.name}}</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="item">
							<div class="ui two column divided stackable grid">
								<div class="column">
									<table class="ui small compact basic table">
										<thead>
											<tr>
												<th class="twelve wide">Continent</th>
												<th class="four wide">Territories</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="continent in continents" ng-click="selectContinent($event, continent)">
												<td class="twelve wide">{{continent.name}}</td>
												<td class="four wide">{{continent.territories.length}}</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="column">
									<table class="ui small compact basic table">
										<thead>
											<tr>
												<th class="five wide">Territory</th>
												<th class="five wide">Owner</th>
												<th class="three wide">Armies</th>
												<th class="three wide">Action</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="territory in territories" ng-click="selectTerritory($event, territory)">
												<td class="five wide">{{territory.name}}</td>
												<td class="five wide">{{territory.garrison.owner.name}}</td>
                        <td class="three wide">{{territory.armyCount}}</td>
                        <td class="three wide"><div class="ui icon army button" ng-show="enableAddArmy(territory)" ng-click="addArmy(territory, 1)">
                          <i class="user icon"></i>
                        </div></td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="ten wide column">
				<div class="ui green segment" ng-controller="MatchBoardMapController">
					<div class="ui green ribbon label">Map</div>
					<div>
						<table class="ui small compact basic table">
							<thead>
								<tr>
									<th class="six wide">Latitude</th>
									<th class="six wide">Longitude</th>
									<th class="four wide">Zoom</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="six wide">{{lastLatitude}}</td>
									<td class="six wide">{{lastLongitude}}</td>
									<td class="four wide">{{lastZoom}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div id="matchMapSegment" class="ui segment">
						<div id="matchMap"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
