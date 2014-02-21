package org.example

import grails.converters.JSON;

import org.example.Party;

import com.dynamix.util.Action;
import com.dynamix.util.Header;
import com.dynamix.util.NavigationUI;

class PartyController {
    static scaffold = true 
	static isAdmin = true
	static isSuperAdmin = false  
	static isDaily = null
	
	static Header navAccordionHeader = Header.ADMIN_HEADER
	static Header navAccordionSubHeader = new Header(id:'partyHeader',messageCode:"nav.header.Party", imagePath:"images/app/party/accordion.png",order:1)
	
	static def navStates = [new NavigationUI(name:'Manage Parties',messageCode:"nav.ManageParties")]
	static def reports = []
	static def primaryFields=null
	static def secondaryFields=null
	static def defaultLabelProp = 'label'
	
	static def actions = [
		Action.DEFAULT_CREATE,
		Action.DEFAULT_EDIT,
		Action.DEFAULT_DELETE
	];

	//Ignore me
	static def navOrder = navAccordionHeader?.order + "-" +navAccordionSubHeader?.order
}
