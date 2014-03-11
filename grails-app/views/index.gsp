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
<script src="${resource(dir: 'js', file: 'imperil.js')}"></script>

</head>

<body ng-controller="mainController">
  <g:render template="/shared/logoutNavigation"/>
  <div id="main">
      <div ng-view></div>
  </div>
	<g:render template="/shared/footer"/>
</body>
</html>
