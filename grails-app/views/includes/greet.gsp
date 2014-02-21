<%@page import="java.lang.Integer"%>
<%@page import="org.example.MarginCallController"%>
<%@page import="com.dynamix.util.Report"%>
<% 
int currentReportIndex = params.currentReport?Integer.valueOf(params.currentReport):0;
Report report = MarginCallController.reports[currentReportIndex];

%>

<script type="text/javascript">
function getTwoChars(date){
	var asString = ''+date;
	return asString.length==1?'0'+asString:asString;
}

var chartTitle = "${report.getTitle()}";
var chartSubTitle = "${report.getSubTitle()}";
var chartXAxisLabel = "${report.getXAxisLabel()}";
var chartYAxisLabel = "${report.getYAxisLabel()}";

var chartType = "${report.chartType}"; 
var currentReportIndex = ${currentReportIndex};

var today = new Date();
var weekAgo = new Date(today.getFullYear(),today.getMonth(),today.getDate()-7,0,0,0,0);

var chartDataUrl = 'marginCall/reportData?reportIndex=' + currentReportIndex;
chartDataUrl += '&start-valuationDate='+weekAgo.getFullYear()+'-'+getTwoChars(weekAgo.getMonth()+1)+'-'+getTwoChars(weekAgo.getDate())+' 00:00:00.000';
chartDataUrl += '&end-valuationDate='+today.getFullYear()+'-'+getTwoChars(today.getMonth()+1)+'-'+getTwoChars(today.getDate())+' 00:00:00.000';
var chart;
</script>

<script src="${resource(dir: 'js', file: 'charts.js')}"></script>

<div>
<h1>Welcome to  <g:message code="application.title"/>!</h1>
		<p>Congratulations from views you have successfully started your first
			Grails application! At the moment this is the default page, feel free
			to modify it to either redirect to a controller or display whatever
			content you may choose. Below is a list of controllers that are
			currently deployed in this application, click on each to execute its
			default action:</p>
		    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
			<div ng-controller="ReportCtrl" ng-init="init(${params.currentReport?:0})" style="padding-top:10px;text-align:right">
			<% 
			  int index = 0;
			  for (Report r in MarginCallController.reports){
				  String reportLabel = index == 0?'Column':index==1?'Pie':'Bar';
				  %> 
				  	<button type="button" class="btn btn-primary" ng-click="reportChanged(${index})" ng-disabled="${currentReportIndex == index}">
				  		<g:message code="${reportLabel}"/>
			  		</button>
				  <%
				index++;
			  }
			%>
			</div>
</div> 