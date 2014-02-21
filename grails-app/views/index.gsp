<!doctype html>
<%@page import="org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Collection"%>
<%@page import="com.dynamix.util.Dashboard"%>
<%@page import="com.dynamix.util.Header"%>
<%@page import="com.dynamix.util.Report"%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="layout" content="main" />
<title><g:message code="application.title"/></title>
		
  		<script src="${resource(dir: 'js', file: 'dynamix.js')}"></script>	
</head>

<body>
	<script>
	var templates = [ { name: 'greet page', url: '/greet'} ];
<% 
		Map<String,Header> navAccordionHeaderMap = new LinkedHashMap<String,Header>();
		def controllerList = grailsApplication.controllerClasses.sort { it.getStaticPropertyValue('navOrder',String.class) };
	    for (DefaultGrailsControllerClass c in controllerList){
			Header navAccordionHeader = c.getStaticPropertyValue('navAccordionHeader',Header.class);
			if(navAccordionHeader!=null){
	    	    if(!navAccordionHeaderMap.containsKey(navAccordionHeader.getLabel())){
					navAccordionHeaderMap.put(navAccordionHeader.getLabel(),navAccordionHeader);
					navAccordionHeader.subHeaders = new ArrayList()
			    }
				Header navAccordionHeaderInUse = navAccordionHeaderMap.get(navAccordionHeader.getLabel());
		
				Header navAccordionSubHeader = c.getStaticPropertyValue('navAccordionSubHeader',Header.class);
				if(navAccordionSubHeader!=null){
					%>
						templates.push({name: "${c.shortName}", url:"/${StringUtils.uncapitalize(c.name)}/index.gsp"});
					<%
					navAccordionHeaderInUse.subHeaders.add(navAccordionSubHeader);
				}
			}		
		}
		%>			
		function ControllerCtrl($scope, $rootScope) {
			$rootScope.templates = templates;
			$rootScope.template = templates[0];
		}
			</script>

<nav class="nav" role="navigation">
	<ul>
		<li><a href="">File</a></li>
		<li><a href="">Actions</a></li>
		<li><a href="">Tools</a></li>
		<li><a href="/j_spring_security_logout">Logout</a></li>
	</ul>
</nav>

	<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;" /></a>
	<div ng-controller="ControllerCtrl"></div>
	<div id="status" class="navStatus status">
    <accordion close-others="true">
		<% 			
	    	boolean first = true;
		int currentIndex = 1;
		for (String headerLabel in navAccordionHeaderMap.keySet()){
			Header navAccordionHeader = navAccordionHeaderMap.get(headerLabel)
			%>
			
			<accordion-group is-open="header${currentIndex}isopen">
				<accordion-heading>
            		<img src="${resource(file: "${navAccordionHeader.imagePath}")}"/>
				    <span class="accordion-label">${headerLabel}</span>
            		<i class="pull-right glyphicon" ng-class="{'icon-chevron-down': header${currentIndex}isopen, 'icon-chevron-right': !header${currentIndex}isopen}"></i>
        		</accordion-heading>
			<%
			if(navAccordionHeader.subHeaders != null && navAccordionHeader.subHeaders.size() > 0){
			  %> <accordion close-others="true"> <%
			for (Header subHeader in navAccordionHeader.subHeaders){
				%>
			<accordion-group is-open="subheader${currentIndex}isopen" ng-click="$root.template = $root.templates[${currentIndex}]">
				<accordion-heading>
            		<img src="${resource(file: "${subHeader.imagePath}")}"/>
				    <span class="accordion-label">${subHeader.getLabel()}</span>
					<i class="pull-right glyphicon" ng-class="{'icon-chevron-down': subheader${currentIndex}isopen, 'icon-chevron-right': !subheader${currentIndex}isopen}"></i>
        		</accordion-heading>
			</accordion-group>
		<%
		  		currentIndex++;
			}
			  %> </accordion> <%
			} 
		%>
			</accordion-group>
			
			<%
		  first = false;
	    }
	%>
	</accordion>
	</div>
	
	<div id="page-body" role="main" class="main-body">
		<div class="reveal main-body" ng-include="template.url+urlParams"/>
	</div>
</body>
</html>
