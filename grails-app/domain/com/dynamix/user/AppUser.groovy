package com.dynamix.user

import java.util.Date;

import com.dynamix.notification.Notification;
import com.dynamix.oauth.OAuthID;
import com.dynamix.organization.Organization;
import com.dynamix.role.Role;
import com.dynamix.usertorole.AppUserToRole;
import com.dynamix.util.NavigationUI;


class AppUser {
	static belongsTo = [organization: Organization]
	static hasMany = [oAuthIDs: OAuthID]
	static constraints = {
			username blank: false, unique: true
			password blank: false
	}
	
	static mapping = { password column: '`password`' }
	static def displayIgnore = ['password']
	
	transient springSecurityService
	
	Date dateCreated
	Date lastUpdated
	Long version
	
	Long id
	String username
	String password
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

	Set<Role> getAuthorities() {
		AppUserToRole.findAllByAppUser(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	
	String toString(){
		return username
	}
	
	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = AppUserController.navStates
	static def reports = AppUserController.reports
	static def primaryFields = AppUserController.primaryFields;
	static def secondaryFields = AppUserController.secondaryFields;
	static def actions = AppUserController.actions;
	static def defaultLabelProp = AppUserController.defaultLabelProp;
}
