package com.dynamix.user

import grails.converters.JSON

import com.dynamix.util.Action;
import com.dynamix.util.Header
import com.dynamix.util.NavigationUI
import com.dynamix.util.SecurityUtils;

class AppUserController {
  static allowedMethods = [resetPassword: "POST"]

  static scaffold = true
  static isAdmin = true
  static isSuperAdmin = false
  static isDaily = null

  static Header navAccordionHeader = Header.ADMIN_HEADER
  static Header navAccordionSubHeader = new Header(id:'appUserHeader', messageCode:"nav.header.AppUser", imagePath:"images/app/user/accordion.png",order:5)

  static def navStates = [
    new NavigationUI(name:'Manage Users',messageCode:"nav.ManageUsers")
  ]
  static def reports = []
  static def primaryFields=[
    'username',
    'dateCreated',
    'lastUpdated',
    'enabled',
    'accountExpired',
    'accountLocked',
    'passwordExpired'
  ]
  static def secondaryFields=null
  static def defaultLabelProp = 'username'

  static def actions = [
    Action.DEFAULT_CREATE,
    Action.DEFAULT_EDIT,
    Action.DEFAULT_DELETE
  ];

  //Ignore me
  static def navOrder = navAccordionHeader?.order + "-" +navAccordionSubHeader?.order

  def resetPassword() {
    Long id = SecurityUtils.getCurrentUserId();
    log.debug('User:['+id+'] called resetPassword called with params:'+params);
    AppUser currentUser = AppUser.get(id)
    currentUser.setPassword(params.password);
    currentUser.save();

    if (!currentUser.save(flush: true)) {
      def listResult = [ success: false, result: currentUser]
      def jsonListResult = listResult as JSON
      log.error('Save had an error:'+jsonListResult);
      render jsonListResult
      return
    }

    def listResult = [ success: true, result: AppUser.get(currentUser.id)]
    def jsonListResult = listResult as JSON
    log.debug('Reset Password success:'+jsonListResult);
    render jsonListResult
  }
}
