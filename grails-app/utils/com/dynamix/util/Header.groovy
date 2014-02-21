package com.dynamix.util

import org.codehaus.groovy.grails.plugins.web.taglib.ValidationTagLib

class Header {

	String id
	String messageCode
	String imagePath
	int order
	List<Header> subHeaders
	
	def g = new ValidationTagLib()
	
	static Header ADMIN_HEADER = new Header(id:'adminHeader',messageCode: "nav.header.Administration",imagePath:"images/app/default/admin/accordion.png", order: 2)
	static Header BUSINESS_HEADER =new Header(id:'businessHeader', messageCode: "nav.header.Business",imagePath:"images/app/default/business/accordion.png", order: 0)
	static Header REPORTS_HEADER =new Header(id:'reportsHeader', messageCode: "nav.header.Reports",imagePath:"images/app/default/admin/accordion.png", order: 1)
	 
	String getLabel(){
		String label = g.message(code:messageCode);
		return label;
	}
	
	String toString(){
		String result ='{"id":"'+id+'","messageCode":"'+messageCode+'","label":"'+getLabel()+'","order":'+order+'}';
		log.error(result);
		return result;
	}
}
