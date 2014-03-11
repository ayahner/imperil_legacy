<div ng-controller="MatchGameController">
  Match Name:{{match.name}}</br>
  Match Description:{{match.description}}</br>
  <accordion>
    <accordion-group ng-repeat="continent in continents">
        <accordion-heading>{{continent.name}}</accordion-heading>
				  <accordion>
				    <accordion-group ng-repeat="territory in continent.territories">
				        <accordion-heading>{{territory.name}}</accordion-heading>
				    </accordion-group>
				  </accordion>
        <div ng-repeat="territory in continent.territories">{{territory.name}}</div>
    </accordion-group>
  </accordion>
  
</div>