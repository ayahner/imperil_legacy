<div ng-controller="MatchGameController">
	<span class="default-label">Match Name</span> {{match.name}}</br> <span
		class="default-label">Match Description</span> {{match.description}}</br> <span
		class="default-label">Match State</span> {{match.state}}</br> <span
		class="default-label">Current Player</span> {{currentPlayer.name}} <span
		class="badge">{{currentPlayer.armyCount}}</span></br> <span
		class="default-label">Match Players</span> {{players.length}}
	<ul>
		<li ng-repeat='player in players'>{{player.name}} <span
			class="badge">{{player.armyCount}}</span></li>
	</ul>
	<span class="default-label">Board</span> {{boardMap.name}}</br> <span
		class="default-label">Territories</span> <span class="badge">{{edgeMap.territoryCount}}</span></br>
	<span class="default-label">Continents</span> <span class="badge">{{continents.length}}</span></br>
	<accordion> <accordion-group
		ng-repeat="continent in continents"> <accordion-heading>{{continent.name}}
	<span class="badge">{{continent.territories.length}}</span></accordion-heading> <accordion>
	<accordion-group ng-repeat="territory in continent.territories">
	<accordion-heading>{{territory.name}} <span
    class="badge">{{territory.armyCount}}</span>
	<button type="button" class="btn btn-default btn-lg" ng-click="addArmy(match, currentPlayer, territory, 1)"><span class="glyphicon glyphicon-star">Place Army</span></button>
	</accordion-heading> </accordion-group> </accordion> </accordion-group> </accordion>
	<span class="default-label">Edges</span> <span class="badge">{{edgeMap.edgeCount}}</span></br>
	<span ng-repeat="edge in edgeMap.edges"> <span>{{edge.sourceContinentName}}
			- {{edge.sourceTerritoryName}} ---> {{edge.destinationContinentName}}
			- {{edge.destinationTerritoryName}}</span></br>
	</span>
</div>