package com.dynamix.notification

import com.dynamix.util.Action;
import com.dynamix.util.Header;

class NotificationController {
	static scaffold = true
	static isAdmin = false
	static isSuperAdmin = false  
	static isDaily = null
	
	static Header navAccordionHeader = null
	static Header navAccordionSubHeader = null
	
	static def navStates=[]
	static def reports=[]
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
