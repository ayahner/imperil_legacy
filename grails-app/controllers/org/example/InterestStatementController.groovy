package org.example

import org.example.ext.InterestStatementBusinessState;
import org.springframework.dao.DataIntegrityViolationException

import com.dynamix.util.Action;
import com.dynamix.util.Header;
import com.dynamix.util.NavigationUI;
import com.dynamix.util.Report;

import grails.converters.JSON;

class InterestStatementController {
	static scaffold = true 
	static isAdmin = false
	static isSuperAdmin = false  
	static isDaily = 'valuationDate'

	static Header navAccordionHeader = Header.BUSINESS_HEADER
	static Header navAccordionSubHeader = new Header(id:'interestStatementHeader',messageCode:"nav.header.InterestStatement", imagePath:"images/app/interestStatement/accordion.png",order:4)
	
	static def subStates = [NavigationUI.navByBusinessState(InterestStatementBusinessState.RECEIVED),NavigationUI.navByBusinessState(InterestStatementBusinessState.UNPAIRED)]
	static def navStates = [NavigationUI.ALL,[NavigationUI.INCOMING,subStates,NavigationUI.OUTGOING,subStates,
			NavigationUI.navByBusinessState(InterestStatementBusinessState.MISMATCHED),
			NavigationUI.navByBusinessState(InterestStatementBusinessState.MATCHED),
			NavigationUI.navByBusinessState(InterestStatementBusinessState.PENDING_VALUE_DATE),
			NavigationUI.navByBusinessState(InterestStatementBusinessState.MATCHED_FINAL),
			NavigationUI.navByBusinessState(InterestStatementBusinessState.CANCELLED)]]
	static def reports = [new Report(titleMessageCode:'nav.reports.InterestStatement.BusinessState', reportLabelFields:['businessState']),
		new Report(titleMessageCode:'nav.reports.InterestStatement.ValuationDate', reportLabelFields:['valuationDate'],chartType:'line')]
	static def primaryFields = ['direction','businessState','marginAgreement','totalCallAmount','type','valuationDate','settlementDate'];
	static def secondaryFields=null
	static def defaultLabelProp = 'totalCallAmount';
	
	static def actions = [
			new Action(messageCode:Action.CREATE_LABEL,path:Action.CREATE_PATH,imagePath:'/images/app/interestStatement/action/create.png',excludeFields: ['dateCreated','lastUpdated','direction','businessState','agreedAmount','counterpartyCollateralBalance','counterpartyInitialMargin','counterpartyMarkToMarket','disputedAmount','cancelComments','disputeComments','partyAExternalReference','partyBExternalReference']),
			new Action(messageCode:'action.intereststatement.clone',path:'/clone',imagePath:'/images/app/interestStatement/action/clone.png',includeFields: []),
			new Action(messageCode:'action.amend',path:'/amend',imagePath:'/images/app/interestStatement/action/amend.png',excludeFields: ['dateCreated','lastUpdated','direction','businessState','agreedAmount','counterpartyCollateralBalance','counterpartyInitialMargin','counterpartyMarkToMarket','disputedAmount','cancelComments','disputeComments','partyAExternalReference','partyBExternalReference']),
			new Action(messageCode:'action.dispute',path:'/dispute',imagePath:'/images/app/interestStatement/action/dispute.png',includeFields: ['disputeComments']),
			new Action(messageCode:'action.intereststatement.force',path:'/force',imagePath:'/images/app/interestStatement/action/force.png',includeFields: ['cancelComments']),
			new Action(messageCode:'action.intereststatement.entervaluedate',path:'/enterValueDate',imagePath:'/images/app/interestStatement/action/enter_value_date.png',includeFields: ['valuationDate']),
			new Action(messageCode:'action.cancel',path:'/cancel',imagePath:'/images/app/interestStatement/action/cancel.png',includeFields: ['cancelComments'])
	];

	//Ignore me
	static def navOrder = navAccordionHeader?.order + "-" +navAccordionSubHeader?.order
}
