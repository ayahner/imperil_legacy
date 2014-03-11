
<div ng-controller="ModalMatchCtrl">
	<script type="text/ng-template" id="createMatchContent.html">
        <div class="modal-header">
            <h3>Create New Match</h3>
        </div>
        <form class="form-horizontal" role="form">
          <div class="form-group">
            <label for="inputName" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="inputName" ng-model="match.name" placeholder="Match Unique Name">
            </div>
          </div>
          <div class="form-group">
            <label for="inputDescription" class="col-sm-2 control-label">Description</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="inputDescription" ng-model="match.description" placeholder="Match Description">
            </div>
          </div>
        <div class="gridStyle" ng-grid="createMatchPlayersGridOptions"/>
        </form>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="ok()">OK</button>
            <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
        </div>
    </script>

	<script type="text/ng-template" id="resumeMatchContent.html">
        <div class="modal-header">
            <h3>Select one match to Resume</h3>
        </div>
        <div class="gridStyle" ng-grid="resumeMatchGridOptions"/>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="ok()">OK</button>
            <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
        </div>
    </script>

	<script type="text/ng-template" id="settingsContent.html">
        <div class="modal-header">
            <h3>Settings</h3>
        </div>
        <form class="form-horizontal" role="form">
          <div class="form-group">
            <label for="inputName" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="inputName" placeholder="Match Unique Name">
            </div>
          </div>
          <div class="form-group">
            <label for="inputDescription" class="col-sm-2 control-label">Description</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="inputDescription" placeholder="Match Description">
            </div>
          </div>
        </form>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="ok()">OK</button>
            <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
        </div>
    </script>

	<script type="text/ng-template" id="toolsContent.html">
        <div class="modal-header">
            <h3>Tools</h3>
        </div>
        <form class="form-horizontal" role="form">
          <div class="form-group">
            <label for="inputName" class="col-sm-2 control-label">Name</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="inputName" placeholder="Match Unique Name">
            </div>
          </div>
          <div class="form-group">
            <label for="inputDescription" class="col-sm-2 control-label">Description</label>
            <div class="col-sm-10">
              <input type="text" class="form-control" id="inputDescription" placeholder="Match Description">
            </div>
          </div>
        </form>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="ok()">OK</button>
            <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
        </div>
    </script>
	<div class="container centerNavBlock">
		<button class="btn btn-default btn-sm btn-block" ng-click="createMatch()">Create Match</button>
		<button class="btn btn-default btn-lg btn-block" ng-click="resumeMatch()">Resume Match</button>
		<button class="btn btn-default btn-lg btn-block" ng-click="settings()">Settings</button>
		<button class="btn btn-default btn-lg btn-block" ng-click="tools()">Tools</button>
	</div>
</div>
