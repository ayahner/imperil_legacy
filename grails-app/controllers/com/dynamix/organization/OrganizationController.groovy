package com.dynamix.organization

import grails.converters.JSON;


import com.dynamix.util.Action;
import com.dynamix.util.Header;
import com.dynamix.util.NavigationUI;

class OrganizationController {
    static scaffold = true 
	static isAdmin = true
	static isSuperAdmin = false 
	static isDaily = null

	static Header navAccordionHeader = Header.ADMIN_HEADER
	static Header navAccordionSubHeader = new Header(id:'organizationHeader',messageCode:"nav.header.Organization", imagePath:"images/app/organization/accordion.png",order:0)
	
	static def navStates = [new NavigationUI(name:'Manage Organizations',messageCode:"nav.ManageOrganizations")]
	static def reports = []
	static def primaryFields=['label','dateCreated','lastModified']
	static def secondaryFields=null
	static def defaultLabelProp = 'label'
	
	static def actions = [
		Action.DEFAULT_CREATE,
		Action.DEFAULT_EDIT,
		Action.DEFAULT_DELETE
	];
	
	//Ignore me
	static def navOrder = navAccordionHeader.order + "-" +navAccordionSubHeader.order
	
}
