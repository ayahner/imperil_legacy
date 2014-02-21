package com.dynamix.userpreference

import java.util.Date;

import com.dynamix.user.AppUser;

class UserPreference {
	static belongsTo = [user: AppUser]
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
		return user?.username+":["+name+"="+value+"]"
	}
	
	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = UserPreferenceController.navStates
	static def reports = UserPreferenceController.reports
	static def primaryFields = UserPreferenceController.primaryFields;
	static def secondaryFields = UserPreferenceController.secondaryFields;
	static def actions = UserPreferenceController.actions;
	static def defaultLabelProp = UserPreferenceController.defaultLabelProp;
}
