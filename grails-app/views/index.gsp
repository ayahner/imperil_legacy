<!doctype html>
<%@page
	import="org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Collection"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="layout" content="main" />
<title><g:message code="application.title" /></title>
<script src="${resource(dir: 'js', file: 'dynamix.js')}"></script>
</head>

<body>
  <g:render template="/shared/logoutNavigation"/>
	<div ng-controller="ModalCreateMatchCtrl">
		<script type="text/ng-template" id="myModalContent.html">
        <div class="modal-header">
            <h3>Create New Match</h3>
        </div>
        <div class="modal-body">
            <ul>
                <li ng-repeat="item in items">
                    <a ng-click="selected.item = item">{{ item }}</a>
                </li>
            </ul>
            Selected: <b>{{ selected.item }}</b>
        </div>
        <div class="modal-footer">
            <button class="btn btn-primary" ng-click="ok()">OK</button>
            <button class="btn btn-warning" ng-click="cancel()">Cancel</button>
        </div>
    </script>

		<div ng-show="selected">Selection from a modal: {{ selected }}</div>

		<div class="container centerNavBlock">
			<button class="btn btn-default btn-sm btn-block" ng-click="open()">Create
				Match</button>
			<button class="btn btn-default btn-lg btn-block" ng-click="open()">Resume
				Match</button>
			<button class="btn btn-default btn-lg btn-block" ng-click="open()">Settings</button>
			<button class="btn btn-default btn-lg btn-block" ng-click="open()">Tools</button>
		</div>
	</div>
	<g:render template="/shared/footer"/>
</body>
</html>
