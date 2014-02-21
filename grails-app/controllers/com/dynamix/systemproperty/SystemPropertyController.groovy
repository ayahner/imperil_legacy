package com.dynamix.systemproperty

import com.dynamix.util.Action;
import com.dynamix.util.Header;
import com.dynamix.util.NavigationUI;

class SystemPropertyController {
    static scaffold = true
	static isAdmin = true
	static isSuperAdmin = true 
	static isDaily = null
	
	static Header navAccordionHeader = Header.ADMIN_HEADER
	static Header navAccordionSubHeader = new Header(id:'systemPropertyHeader', messageCode:"nav.header.SystemProperty", imagePath:"images/app/systemProperty/accordion.png",order:3)
	
	static def navStates=[new NavigationUI(name:'Manage System Properties',messageCode:"nav.ManageSystemProperties")]
	static def reports=[];
	static def primaryFields=null;
	static def secondaryFields=null;
	static def defaultLabelProp = 'label';
	
	static def actions = [
		Action.DEFAULT_CREATE,
		Action.DEFAULT_EDIT,
		Action.DEFAULT_DELETE
	];
	
	//Ignore me
	static def navOrder = navAccordionHeader?.order + "-" +navAccordionSubHeader?.order
}
