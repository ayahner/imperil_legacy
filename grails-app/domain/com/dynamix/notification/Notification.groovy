package com.dynamix.notification

import com.dynamix.user.AppUser;

class Notification {
	static belongsTo = [user: AppUser]
	static constraints = {
		level(blank:false)
		message(blank:false)
	}
	static mapping = {
		sort lastUpdated: "desc"
    }
	
	Date dateCreated
	Date lastUpdated
	Long version
	
	String level
	String message
	
	String toString(){
		return message
	}
	
	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates =NotificationController.navStates
	static def reports = NotificationController.reports
	static def primaryFields = NotificationController.primaryFields;
	static def secondaryFields = NotificationController.secondaryFields;
	static def actions = NotificationController.actions;
	static def defaultLabelProp = NotificationController.defaultLabelProp;
}
