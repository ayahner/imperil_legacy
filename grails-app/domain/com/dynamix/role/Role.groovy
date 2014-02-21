package com.dynamix.role

import java.util.Date;

import com.dynamix.util.NavigationUI;

class Role {
    static belongsTo = []
	static hasMany = []
	static mapping = { cache true }
	static constraints = {
		authority blank: false, unique: true
	}
	
	Date dateCreated
	Date lastUpdated
	Long version
	
	String authority
	
	String toString(){
		return authority
	}
	
	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = RoleController.navStates
	static def reports = RoleController.reports
	static def primaryFields = RoleController.primaryFields;
	static def secondaryFields = RoleController.secondaryFields;
	static def actions = RoleController.actions;
	static def defaultLabelProp = RoleController.defaultLabelProp;
}
