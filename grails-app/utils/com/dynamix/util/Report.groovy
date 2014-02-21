package com.dynamix.util

import grails.converters.JSON;
import org.codehaus.groovy.grails.plugins.web.taglib.ValidationTagLib

class Report {

	def g = new ValidationTagLib()
	
	String titleMessageCode
	String subtitleMessageCode
	String xAxisLabelMessageCode
	String yAxisLabelMessageCode

	String chartType = 'pie' // supports pie, column, bar
	List<String> reportLabelFields
	
	String getTitle(){
		return g.message(code:titleMessageCode);
	}
	String getSubTitle(){
		return g.message(code:subtitleMessageCode);
	}
	String getXAxisLabel(){
		return g.message(code:xAxisLabelMessageCode);
	}
	String getYAxisLabel(){
		return g.message(code:yAxisLabelMessageCode);
	}
}
