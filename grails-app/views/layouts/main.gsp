<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6" ng-app> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7" ng-app> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8" ng-app> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9" ng-app> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!-->
<html lang="en" class="no-js" ng-app="main">
<!--<![endif]-->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title><g:layoutTitle default="Application" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="icon" type="image/png"
	href="${resource(dir: 'images', file: 'favicon.ico')}" />

<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'initial.css')}" type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
<link rel="stylesheet" href="${resource(dir: 'css', file: 'index.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'bootstrap-v2.3.2/css', file: 'bootstrap.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'angular/docs/css', file: 'animations.css')}"
	type="text/css">
<link rel="stylesheet"
	href="${resource(dir: 'angular-ui-ng-grid', file: 'ng-grid.css')}"
	type="text/css">

<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}"
	type="text/css">
<script src="${resource(dir: 'jquery', file: 'jquery-1.10.2.js')}"></script>
<script src="${resource(dir: 'bootstrap-v2.3.2/js', file: 'bootstrap.js')}"></script>
<script src="${resource(dir: 'angular', file: 'angular.js')}"></script>
<script src="${resource(dir: 'angular', file: 'angular-animate.js')}"></script>
<script src="${resource(dir: 'angular-ui-bootstrap', file: 'ui-bootstrap-0.7.0.js')}"></script>
<script	src="${resource(dir: 'angular-ui-ng-grid', file: 'ng-grid-2.0.7.debug.js')}"></script>
<!--   		
      <script src="${resource(dir: 'highcharts-3.0.8/js', file: 'highcharts.js')}"></script>
-->
<script src="${resource(dir: 'js', file: 'dynamix-common.js')}"></script>
<!--      
  		<script src="${resource(dir: 'js', file: 'init-charts.js')}"></script>		
-->
<g:layoutHead />
<r:layoutResources />
</head>
<body>
	<div class="header " role="banner">
		<a href="http://localhost"> <img class="header-image"
			src="${resource(dir: 'images/imperil', file: 'imperil_logo.png')}"
			alt="Imperil" /> <img class="header-small-image"
			src="${resource(dir: 'images/imperil', file: 'imperil_small_logo.png')}"
			alt="Imperil" /> <img class="header-small-image"
			src="${resource(dir: 'images/imperil', file: 'imperil_small_text.png')}"
			alt="Imperil" />
		</a>
	</div>
	<div class="main-body">
		<g:layoutBody />
	</div>
	<div id="spinner" class="spinner" style="display: none;">
		<g:message code="spinner.alt" default="Loading&hellip;" />
	</div>
	<r:layoutResources />
</body>
</html>
