package com.dynamix.systemproperty

import java.util.Date;

class SystemProperty {
    static belongsTo = []
	static hasMany = []
	static constraints = {
	}
	static mapping = {
		sort lastUpdated: "desc"
    }
	
	Date dateCreated
	Date lastUpdated
	Long version
	
	String name
	String value

	String toString(){
		return name+"="+value
	}
	
	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = SystemPropertyController.navStates
	static def reports = SystemPropertyController.reports
	static def primaryFields = SystemPropertyController.primaryFields;
	static def secondaryFields = SystemPropertyController.secondaryFields;
	static def actions = SystemPropertyController.actions;
	static def defaultLabelProp = SystemPropertyController.defaultLabelProp;
}
