package com.dynamix.role

import grails.converters.JSON

import com.dynamix.util.Action;
import com.dynamix.util.Header
import com.dynamix.util.NavigationUI

class RoleController {
  static scaffold = true
  static isAdmin = false
  static isSuperAdmin = true
  static isDaily = null

  static Header navAccordionHeader = Header.ADMIN_HEADER
  static Header navAccordionSubHeader = new Header(id:'roleHeader',messageCode:"nav.header.Role", imagePath:"images/app/role/accordion.png", order:2)

  static def navStates=[
    new NavigationUI(name:'Manage Roles',messageCode:"nav.ManageRoles")
  ]
  static def reports = []
  static def primaryFields=null
  static def secondaryFields=null
  static def defaultLabelProp = 'label'

  static def actions = [
    Action.DEFAULT_CREATE,
    Action.DEFAULT_EDIT,
    Action.DEFAULT_DELETE
  ];
}
