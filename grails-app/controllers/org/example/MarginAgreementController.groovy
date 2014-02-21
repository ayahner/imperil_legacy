package org.example

import grails.converters.JSON

import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty
import org.example.ext.MarginAgreementBusinessState;
import org.joda.time.LocalDate

import com.dynamix.util.Action;
import com.dynamix.util.DomainUtils
import com.dynamix.util.Header
import com.dynamix.util.NavigationUI;

class MarginAgreementController {
	static scaffold = true	
	static isAdmin = false
	static isSuperAdmin = false
	static isDaily = null

	static Header navAccordionHeader = Header.BUSINESS_HEADER
	static Header navAccordionSubHeader = new Header(id:'marginAgreementHeader',messageCode:"nav.header.MarginAgreement", imagePath:"images/app/agreement/accordion.png",order:0)
	
	static def pendingStates = [NavigationUI.navByBusinessState(MarginAgreementBusinessState.PENDING_NEW),
		NavigationUI.navByBusinessState(MarginAgreementBusinessState.PENDING_REJECTED),
		NavigationUI.navByBusinessState(MarginAgreementBusinessState.PENDING_CHANGE),
		NavigationUI.navByBusinessState(MarginAgreementBusinessState.PENDING_DISCONTINUATION)]
	static def navStates=[NavigationUI.ALL,[new NavigationUI(name:'Pending',messageCode:"nav.state.Pending",urlParams:'&businessState=Pending'),
			[NavigationUI.INCOMING,pendingStates,NavigationUI.OUTGOING,pendingStates],
			NavigationUI.navByBusinessState(MarginAgreementBusinessState.ACTIVE),
			NavigationUI.navByBusinessState(MarginAgreementBusinessState.CANCELLED)]]
	
	static def reports = []
	static def primaryFields = ['shortName','longName','direction','businessState','partyA','partyB','activeDate','agreementType','timeZone'];
	static def secondaryFields=null
	static def defaultLabelProp = 'shortName';
	
	static def commonExcludeFields =['dateCreated','lastUpdated','acceptedBy','acceptedBy','businessState','cancelComments','createdBy','direction','discontinueDate','lastModifiedParty','rejectComments','rejectedBy']; 
	static def actions = [
		new Action(messageCode:Action.CREATE_LABEL,path:Action.CREATE_PATH,imagePath:'/images/app/agreement/action/create.png',excludeFields: commonExcludeFields),
		new Action(messageCode:Action.EDIT_LABEL,path:'/edit',imagePath:'/images/app/agreement/action/edit.png',showMap:[:],excludeFields: commonExcludeFields),
		new Action(messageCode:'action.cancel',path:'/cancel',imagePath:'/images/app/agreement/action/cancel.png',showMap:['businessState':[MarginAgreementBusinessState.PENDING_NEW.state,MarginAgreementBusinessState.PENDING_REJECTED.state,MarginAgreementBusinessState.PENDING_CHANGE.state,MarginAgreementBusinessState.PENDING_DISCONTINUATION.state,MarginAgreementBusinessState.ACTIVE.state]],includeFields: ['cancelComments']),
		new Action(messageCode:'action.amend',path:'/amend',imagePath:'/images/app/agreement/action/amend.png',showMap:['businessState':[MarginAgreementBusinessState.PENDING_REJECTED.state,MarginAgreementBusinessState.PENDING_CHANGE.state,MarginAgreementBusinessState.ACTIVE.state]],excludeFields: commonExcludeFields),
		new Action(messageCode:'action.accept',path:'/accept',imagePath:'/images/app/agreement/action/accept.png',showMap:['businessState':[MarginAgreementBusinessState.PENDING_NEW.state,MarginAgreementBusinessState.PENDING_CHANGE.state,MarginAgreementBusinessState.PENDING_DISCONTINUATION.state]],includeFields: ['shortName']),
		new Action(messageCode:'action.reject',path:'/reject',imagePath:'/images/app/agreement/action/reject.png',showMap:['businessState':[MarginAgreementBusinessState.PENDING_NEW.state,MarginAgreementBusinessState.PENDING_CHANGE.state,MarginAgreementBusinessState.PENDING_DISCONTINUATION.state]],includeFields: ['rejectComments']),
		new Action(messageCode:'action.marginagreement.discontinue',path:'/discontinue',imagePath:'/images/app/agreement/action/discontinue.png',showMap:['businessState':[MarginAgreementBusinessState.ACTIVE.state]],includeFields: ['discontinueDate']),
	];

	def create() {
		params.businessState=MarginAgreementBusinessState.PENDING_NEW.state;
		[marginAgreement: new MarginAgreement(params)]
	}
	
	def edit() {
		update(DomainUtils.safeLong(params.id),DomainUtils.safeLong(params.version));
	}
	
	def cancel() {
		params.businessState=MarginAgreementBusinessState.CANCELLED.state;
		update(DomainUtils.safeLong(params.id),DomainUtils.safeLong(params.version));
	}
	
	def amend() {
		params.businessState=MarginAgreementBusinessState.PENDING_CHANGE.state;
		update(DomainUtils.safeLong(params.id),DomainUtils.safeLong(params.version));
	}
	
	def accept() {
		params.businessState=MarginAgreementBusinessState.ACTIVE.state;
		update(DomainUtils.safeLong(params.id),DomainUtils.safeLong(params.version));
	}
	
	def reject() {
		params.businessState=MarginAgreementBusinessState.PENDING_REJECTED.state;
		update(DomainUtils.safeLong(params.id),DomainUtils.safeLong(params.version));
	}
	
	def discontinue() {
		params.businessState=MarginAgreementBusinessState.PENDING_DISCONTINUATION.state;
		update(DomainUtils.safeLong(params.id),DomainUtils.safeLong(params.version));
	}
	
	//Ignore me
	static def navOrder = navAccordionHeader?.order + "-" +navAccordionSubHeader?.order
}
