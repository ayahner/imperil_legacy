package org.example

import java.util.Date;

import com.dynamix.organization.Organization;
import com.dynamix.util.NavigationUI;

class Party {
	static belongsTo = [organization: Organization]
	static hasMany = []
	static constraints = {
		label(blank:false)
	}
	static mapping = {
		sort lastUpdated: "desc"
    	}
	
	Date dateCreated
	Date lastUpdated
	Long version
	
	String label

	
	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = PartyController.navStates
	static def reports = PartyController.reports
	static def primaryFields = PartyController.primaryFields;
	static def secondaryFields = PartyController.secondaryFields;
	static def actions = PartyController.actions;
	static def defaultLabelProp = PartyController.defaultLabelProp;
}
