<div ng-controller="MatchGameController">
  Match Name:{{match.name}}</br>
  Match Description:{{match.description}}</br>
  <div>match.edges</div>
  <accordion>
    <accordion-group ng-repeat="continent in continents">
        <accordion-heading>{{continent.name}}</accordion-heading>
				  <accordion>
				    <accordion-group ng-repeat="territory in continent.territories">
				        <accordion-heading>{{territory.name}}</accordion-heading>
				    </accordion-group>
				  </accordion>
    </accordion-group>
  </accordion>
</div>