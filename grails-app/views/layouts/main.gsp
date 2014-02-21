<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6" ng-app> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7" ng-app> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8" ng-app> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9" ng-app> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js" ng-app="main"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="Application"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		
		<link rel="icon" type="image/png" href="${resource(dir: 'images', file: 'favicon.ico')}" />
		
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'initial.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'index.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'bootstrap-v2.3.2/css', file: 'bootstrap.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'angular/docs/css', file: 'animations.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'angular-ui-ng-grid', file: 'ng-grid.css')}" type="text/css">
		
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		
  		<script src="${resource(dir: 'jquery', file: 'jquery-1.10.2.js')}"></script>
  		<script src="${resource(dir: 'bootstrap-v2.3.2/js', file: 'bootstrap.js')}"></script>
  		<script src="${resource(dir: 'angular', file: 'angular.js')}"></script>
  		<script src="${resource(dir: 'angular', file: 'angular-animate.js')}"></script>
  		<script src="${resource(dir: 'angular-ui-bootstrap', file: 'ui-bootstrap-0.7.0.js')}"></script>
  		<script src="${resource(dir: 'angular-ui-ng-grid', file: 'ng-grid-2.0.7.debug.js')}"></script>
		<script src="${resource(dir: 'highcharts-3.0.8/js', file: 'highcharts.js')}"></script>
  		
  		<script src="${resource(dir: 'js', file: 'dynamix-common.js')}"></script>	  		
  		<script src="${resource(dir: 'js', file: 'init-charts.js')}"></script>		

		<g:layoutHead/>
		<r:layoutResources />
	</head>
	<body>
		<div class="header" role="banner">
			<a href="http://localhost">
				<img class="header-image"       src="${resource(dir: 'images/acadia', file: 'acadiasoft_logo.png')}" alt="Marginsphere"/>
				<img class="header-small-image" src="${resource(dir: 'images/acadia', file: 'acadiasoft_small_logo.png')}" alt="Marginsphere"/>
				<img class="header-small-image" src="${resource(dir: 'images/acadia', file: 'acadiasoft_small_text.png')}" alt="Marginsphere"/>
			</a>
		</div>
		<div class="main-body">
			<g:layoutBody/>
		</div>
		<div class="footer" role="contentinfo" class="footer">
			
<div id="footerOauthImages" class="footer-oauth-images">		
<s2o:ifLoggedInWith provider="facebook">
					<img src="${resource(dir: 'images/app/auth', file: 'facebook_icon.png')}" alt="Facebook" width="25px" height="25px">
</s2o:ifLoggedInWith>
<s2o:ifNotLoggedInWith provider="facebook">
					<img class="desaturate" src="${resource(dir: 'images/app/auth', file: 'facebook_icon.png')}" alt="Facebook" width="25px" height="25px">
</s2o:ifNotLoggedInWith>

<s2o:ifLoggedInWith provider="google">
					<img src="${resource(dir: 'images/app/auth', file: 'google_icon.png')}" alt="google" width="25px" height="25px">
</s2o:ifLoggedInWith>
<s2o:ifNotLoggedInWith provider="google">
					<img class="desaturate" src="${resource(dir: 'images/app/auth', file: 'google_icon.png')}" alt="google" width="25px" height="25px">
</s2o:ifNotLoggedInWith>

<s2o:ifLoggedInWith provider="twitter">
					<img src="${resource(dir: 'images/app/auth', file: 'twitter_icon.png')}" alt="twitter" width="25px" height="25px">
</s2o:ifLoggedInWith>
<s2o:ifNotLoggedInWith provider="twitter">
					<img class="desaturate" src="${resource(dir: 'images/app/auth', file: 'twitter_icon.png')}" alt="twitter" width="25px" height="25px">
</s2o:ifNotLoggedInWith>
</div>	

		<g:message code="application.title"/> &copy;2014</div>
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<r:layoutResources />
	</body>
</html>
