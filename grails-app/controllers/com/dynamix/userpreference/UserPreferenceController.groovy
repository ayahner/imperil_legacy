package com.dynamix.userpreference

import com.dynamix.util.Action;
import com.dynamix.util.Header;
import com.dynamix.util.NavigationUI;

class UserPreferenceController {
  static scaffold = true
  static isAdmin = true
  static isSuperAdmin = false
  static isDaily = null

  static Header navAccordionHeader = Header.ADMIN_HEADER
  static Header navAccordionSubHeader = new Header(id:'userPreferenceHeader', messageCode:"nav.header.UserPreference", imagePath:"images/app/userPreference/accordion.png",order:4)

  static def navStates=[
    new NavigationUI(name:'Manage User Preferences',messageCode:"nav.ManageUserPreferences")
  ];
  static def reports=[];
  static def primaryFields=['user', 'name', 'value'];
  static def secondaryFields=null;
  static def defaultLabelProp = 'label';

  static def actions = [
    Action.DEFAULT_CREATE,
    Action.DEFAULT_EDIT,
    Action.DEFAULT_DELETE
  ];

  //Ignore me
  static def navOrder = navAccordionHeader?.order + "-" +navAccordionSubHeader?.order
}
