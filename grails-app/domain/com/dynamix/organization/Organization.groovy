package com.dynamix.organization

import java.util.Date;
import com.dynamix.util.NavigationUI;

class Organization {
    static belongsTo = []
	static constraints = {
	}
	static mapping = {
		sort lastUpdated: "desc"
    }
	
	Date dateCreated
	Date lastUpdated
	Long version

	String label

	String toString(){
		return label
	}
	
	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = OrganizationController.navStates
	static def reports = OrganizationController.reports
	static def primaryFields = OrganizationController.primaryFields;
	static def secondaryFields = OrganizationController.secondaryFields;
	static def actions = OrganizationController.actions;
	static def defaultLabelProp = OrganizationController.defaultLabelProp;
}
