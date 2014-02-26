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
  <div class="">This is the Initialize Match screen<br/>I assume you have already invited the players and been accepted</div>
  <g:render template="/shared/footer"/>
</body>
</html>
