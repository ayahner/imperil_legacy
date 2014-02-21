/**
 * Request data from the server, add it to the graph and set a timeout to
 * request again
 */
function requestData() {
	$.ajax({
		url : chartDataUrl,
		success : function(data) {
			chart.series[0].setData(data);
			console.log("finished setting data on chart:" + chart);
		},
		cache : false
	});
};

$(function() {
	// Build the chart
	chart = new Highcharts.Chart(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false,
							renderTo: 'container',
							events : {
								load : requestData
							}
						},
						title : {
							text : chartTitle
						},
						subtitle : {
							text : chartSubTitle
						},
						xAxis : {
						    type: 'category',
							title : {
								text : chartXAxisLabel
							}
						},
						yAxis : {
							title : {
								text : chartYAxisLabel
							}
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.percentage:.1f}%</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								dataLabels : {
									enabled : true,
									color : '#000000',
									connectorColor : '#000000',
									formatter : function() {
										return '<b>' + this.point.name
												+ '</b>: ' + this.percentage
												+ ' %';
									}
								}
							},
							bar : {
								dataLabels : {
									enabled : true,
									color : '#000000'
								}
							},
							column : {
								dataLabels : {
									enabled : true,
									color : '#000000'
								}
							}
						},
						series : [ {
							type : chartType,
							name : chartYAxisLabel,
							data : []
						} ]
					});
});
