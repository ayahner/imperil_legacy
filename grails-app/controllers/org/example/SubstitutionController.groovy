package org.example

import org.example.ext.SubstitutionBusinessState;

import grails.converters.JSON;

import com.dynamix.util.Action;
import com.dynamix.util.Header;
import com.dynamix.util.NavigationUI;
import com.dynamix.util.Report;

class SubstitutionController {
    static scaffold = true 
	static isAdmin = false
	static isSuperAdmin = false 
	static isDaily = 'valuationDate'
	
	static Header navAccordionHeader = Header.BUSINESS_HEADER
	static Header navAccordionSubHeader = new Header(id:'substitutionHeader',messageCode:"nav.header.Substitution", imagePath:"images/app/substitution/accordion.png", order:3)
	
	static def subStates = [NavigationUI.navByBusinessState(SubstitutionBusinessState.RECEIVED),NavigationUI.navByBusinessState(SubstitutionBusinessState.ACCEPTED),NavigationUI.navByBusinessState(SubstitutionBusinessState.REJECTED)]
	static def navStates = [NavigationUI.ALL,[NavigationUI.INCOMING,subStates,NavigationUI.OUTGOING,subStates,NavigationUI.navByBusinessState(SubstitutionBusinessState.CANCELLED)]]
	static def reports = [new Report(titleMessageCode:'nav.reports.Substitution.BusinessState', reportLabelFields:['businessState']),
		new Report(titleMessageCode:'nav.reports.Substitution.ValuationDate', reportLabelFields:['valuationDate'],chartType:'bar')]
	static def primaryFields = ['direction','businessState','marginAgreement','totalCallAmount','type','valuationDate','settlementDate'];
	static def secondaryFields=null
	static def defaultLabelProp = 'totalCallAmount';
	
	static def actions = [
		new Action(messageCode:Action.CREATE_LABEL,path:Action.CREATE_PATH,imagePath:'/images/app/substitution/action/create.png',excludeFields: ['dateCreated','lastUpdated','direction','businessState','agreedAmount','counterpartyCollateralBalance','counterpartyInitialMargin','counterpartyMarkToMarket','disputedAmount','cancelComments','disputeComments','partyAExternalReference','partyBExternalReference']),
		new Action(messageCode:'action.accept',path:'/accept',imagePath:'/images/app/substitution/action/accept.png',showMap:['businessState':[SubstitutionBusinessState.RECEIVED.state]],includeFields: []),
		new Action(messageCode:'action.reject',path:'/reject',imagePath:'/images/app/substitution/action/reject.png',showMap:['businessState':[SubstitutionBusinessState.RECEIVED.state]],includeFields: ['rejectComments']),
		new Action(messageCode:'action.amend',path:'/amend',imagePath:'/images/app/substitution/action/amend.png',showMap:['businessState':[SubstitutionBusinessState.RECEIVED.state]],excludeFields: ['dateCreated','lastUpdated','direction','businessState','agreedAmount','counterpartyCollateralBalance','counterpartyInitialMargin','counterpartyMarkToMarket','disputedAmount','cancelComments','disputeComments','partyAExternalReference','partyBExternalReference']),
		new Action(messageCode:'action.cancel',path:'/cancel',imagePath:'/images/app/substitution/action/cancel.png',showMap:['businessState':[SubstitutionBusinessState.RECEIVED.state]],includeFields: ['cancelComments']),
		new Action(messageCode:'action.substitution.revise',path:'/revise',imagePath:'/images/app/substitution/action/revise.png',showMap:['businessState':[SubstitutionBusinessState.REJECTED.state]],excludeFields: ['dateCreated','lastUpdated','direction','businessState','agreedAmount','counterpartyCollateralBalance','counterpartyInitialMargin','counterpartyMarkToMarket','disputedAmount','cancelComments','disputeComments','partyAExternalReference','partyBExternalReference']),
		new Action(messageCode:'action.substitution.reviseaccept',path:'/reviseAccept',imagePath:'/images/app/substitution/action/revise_accept.png',showMap:['businessState':[SubstitutionBusinessState.RECEIVED.state]],includeFields:[])
	];
 
	//Ignore me
	static def navOrder = navAccordionHeader?.order + "-" +navAccordionSubHeader?.order
}
