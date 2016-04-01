
<div class="ui basic segment" ng-controller="EditBoardMapController">
	<div ng-controller="UploadController">
		<div class="ui two column stackable grid">
			<div class="six wide column">
				<div class="ui red segment" ng-file-drop ng-file-over="tertiary inverted">
					<div class="ui left floated red ribbon label">Map: {{boardMap.name}}</div>
					<div id='boardMapControls' class="ui right floated tiny icon overlay buttons">
						<a class="ui button" ng-href="/boardMap/export?id={{boardMap.id}}" target="_self"> <i class="cloud download icon"></i>
						</a>
						<div class="or"></div>
						<div class="ui button" ng-click="uploadClick(boardMap.id)">
							<i class="cloud upload icon"></i>
						</div>
						<div class="ui button" ng-click="showUploadDetails = !showUploadDetails">
							<i class="unhide icon" ng-show="showUploadDetails"></i> <i class="hide icon" ng-show="!showUploadDetails"></i>
						</div>
					</div>
					<div class="ui small hidable file uploader basic fitted segment" ng-style="{ 'height': showUploadDetails ? '100%' : '0px' }" ng-class="{ 'hidden': !showUploadDetails }">
						<div class="ui uploader segment">
							<input id="uploadInput" ng-file-select type="file" style="display: none" multiple /> <span class="ui small header">Upload Queue</span>
							<p>Queue length: {{ uploader.queue.length }}</p>
							<table class="ui small compact basic table">
								<thead>
									<tr>
										<th class="eight wide">Name</th>
										<th ng-show="uploader.isHTML5">Size</th>
										<th ng-show="uploader.isHTML5">Progress</th>
										<th>Status</th>
										<th>Actions</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-repeat="item in uploader.queue">
										<td><strong>{{ item.file.name }}</strong></td>
										<td ng-show="uploader.isHTML5" nowrap>{{ item.file.size/1024/1024|number:2 }} MB</td>
										<td ng-show="uploader.isHTML5">
											<div class="ui progress">
												<div class="bar" role="progressbar" ng-style="{ 'width': item.progress + '%' }"></div>
											</div>
										</td>
										<td><span ng-show="item.isSuccess"><i class="positive tiny ok icon"></i></span> <span ng-show="item.isCancel"><i class="neutral tiny ban circle icon"></i></span> <span
											ng-show="item.isError"><i class="negative tiny remove circle icon"></i></span></td>
										<td nowrap>
											<button type="button" class="ui tiny positive left attached icon button" ng-click="item.upload()" ng-disabled="item.isReady || item.isUploading || item.isSuccess">
												<i class="upload icon"></i>
											</button>
											<button type="button" class="ui tiny neutral icon button" ng-click="item.cancel()" ng-disabled="!item.isUploading">
												<i class="ban circle icon"></i>
											</button>
											<button type="button" class="ui tiny negative right attached icon button" ng-click="item.remove()">
												<i class="trash icon"></i>
											</button>
										</td>
									</tr>
								</tbody>
							</table>

							<div>
								<p>Queue progress:
								<div class="ui progress">
									<div class="bar" role="progressbar" ng-style="{ 'width': uploader.progress + '%' }"></div>
								</div>
								</p>
								<div class="ui tiny buttons">
									<button class="ui positive icon button" ng-click="uploader.uploadAll()" ng-disabled="!uploader.getNotUploadedItems().length">
										<i class="upload icon"></i> Upload all
									</button>
									<button class="ui neutral icon button" ng-click="uploader.cancelAll()" ng-disabled="!uploader.isUploading">
										<i class="ban circle icon"></i> Cancel all
									</button>
									<button class="ui negative icon button" ng-click="uploader.clearQueue()" ng-disabled="!uploader.queue.length">
										<i class="trash icon"></i> Remove all
									</button>
								</div>
							</div>
						</div>
					</div>
					<h3 ng-click="showBoardDetails = !showBoardDetails">Details</h3>
					<div class="description">{{boardMap.description}}</div>
					<div class="ui large hidable list" ng-class="{ 'hidden': showBoardDetails }">
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
												<th class="ten wide">Continent</th>
												<th class="six wide"># Territories</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="continent in boardMap.continents" ng-class="continent == selectedContinent?'active':''" ng-click="selectContinent($event, continent)">
												<td class="ten wide">{{continent.name}}</td>
												<td class="six wide">{{continent.territories.length}}</td>
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
												<th class="three wide">Actions</th>
											</tr>
										</thead>
										<tbody>
											<tr ng-repeat="territory in selectedContinent.territories" ng-class="territory == selectedTerritory?'active':''" ng-click="selectTerritory($event, territory)">
												<td class="five wide">{{territory.name}}</td>
												<td class="five wide">{{territory.garrison.owner.name}}</td>
												<td class="three wide"></td>
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
				<div class="ui green segment" ng-controller="BoardMapViewController">
					<div class="ui green ribbon label">Map</div>
					<div>
						<table class="ui small compact basic table">
							<thead>
								<tr>
									<th class="four wide">Latitude</th>
									<th class="four wide">Longitude</th>
									<th class="four wide">Zoom</th>
									<th class="four wide">Pending Save</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td class="four wide">{{lastLatitude}}</td>
									<td class="four wide">{{lastLongitude}}</td>
									<td class="two wide">{{lastZoom}}</td>
									<td class="two wide">{{selectedOverlayPendingSave}}</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div id='overlaycontrols' class="ui icon overlay buttons">
						<div class="ui tiny add overlay button" ng-disabled="!showAddButton()" ng-class="{'disabled': !showAddButton() }" ng-click="addOverlay()">
							<i class="add icon"></i>
						</div>
            <div class="ui tiny remove overlay button" ng-disabled="!showRemoveButton()" ng-class="{'disabled': !showRemoveButton() }" ng-click="removeOverlay()">
              <i class="remove icon"></i>
            </div>
            <div class="ui tiny edit overlay button" ng-disabled="!showEditButton()" ng-class="{'disabled': !showEditButton() }" ng-click="editOverlay()">
              <i class="edit icon"></i>
            </div>
						<div class="ui tiny save overlay button" ng-disabled="!showSaveButton()" ng-class="{'disabled': !showSaveButton() }" ng-click="saveOverlay()">
							<i class="save icon"></i>
						</div>
					</div>
					<div id="boardMapSegment" class="ui segment">
						<div id="boardMap"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>