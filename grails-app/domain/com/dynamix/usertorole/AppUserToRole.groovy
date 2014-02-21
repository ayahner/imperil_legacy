package com.dynamix.usertorole

import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder

import com.dynamix.role.Role;
import com.dynamix.user.AppUser;


class AppUserToRole implements Serializable {

	
	Date dateCreated
	Date lastUpdated
	Long version
	
	AppUser appUser
	Role role

	boolean equals(other) {
		if (!(other instanceof AppUserToRole)) {
			return false
		}

		other.appUser?.id == appUser?.id &&
			other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (appUser) builder.append(appUser.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static AppUserToRole get(long appUserId, long roleId) {
		find 'from AppUserRole where appUser.id=:appUserId and role.id=:roleId',
			[appUserId: appUserId, roleId: roleId]
	}

	static AppUserToRole create(AppUser appUser, Role role, boolean flush = false) {
		new AppUserToRole(appUser: appUser, role: role).save(flush: flush, insert: true)
	}

	static boolean remove(AppUser appUser, Role role, boolean flush = false) {
		AppUserToRole instance = AppUserToRole.findByAppUserAndRole(appUser, role)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(AppUser appUser) {
		executeUpdate 'DELETE FROM AppUserRole WHERE appUser=:appUser', [appUser: appUser]
	}

	static void removeAll(Role role) {
		executeUpdate 'DELETE FROM AppUserRole WHERE role=:role', [role: role]
	}

	static mapping = {
		id composite: ['role', 'appUser']
		version false
	}
	
	String toString(){
		return role
	}
}
