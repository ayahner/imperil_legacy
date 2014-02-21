package org.example

import grails.converters.JSON;
import grails.orm.HibernateCriteriaBuilder;
import grails.transaction.Transactional;

import org.codehaus.groovy.grails.commons.GrailsDomainClass;
import org.example.ext.MarginCallBusinessState;

import com.dynamix.hibernate.HibernateUtils;
import com.dynamix.util.Action;
import com.dynamix.util.Header
import com.dynamix.util.NavigationUI;
import com.dynamix.util.Report;

class MarginCallController {
	static scaffold = true
	static isAdmin = false
	static isSuperAdmin = false
	static isDaily = 'valuationDate'
	
	static navigationScope = 'primary'

	static Header navAccordionHeader = Header.BUSINESS_HEADER
	static Header navAccordionSubHeader = new Header(id:'marginCallHeader',messageCode:"nav.header.MarginCall", imagePath:"images/app/marginCall/accordion.png", order:1)
	
	static def subStates = [NavigationUI.navByBusinessState(MarginCallBusinessState.RECEIVED),
		NavigationUI.navByBusinessState(MarginCallBusinessState.AGREED),
		new NavigationUI(name:MarginCallBusinessState.PLEDGED.state,messageCode:"nav.state."+MarginCallBusinessState.PLEDGED,urlParams:'&businessState=Pledge'),
				[NavigationUI.navByBusinessState(MarginCallBusinessState.PLEDGED),NavigationUI.navByBusinessState(MarginCallBusinessState.PLEDGE_ACCEPTED),NavigationUI.navByBusinessState(MarginCallBusinessState.PLEDGE_REJECTED)],
				NavigationUI.navByBusinessState(MarginCallBusinessState.DISPUTED)]
	static def navStates = [NavigationUI.ALL,[NavigationUI.INCOMING,subStates,NavigationUI.OUTGOING,subStates,NavigationUI.navByBusinessState(MarginCallBusinessState.CANCELLED)]]
	static def reports = [
		new Report(titleMessageCode:'nav.reports.MarginCall.ValuationDate', subtitleMessageCode:'nav.reports.MarginCall.ValuationDate.subtitle', xAxisLabelMessageCode:'nav.reports.MarginCall.ValuationDate.xAxis', yAxisLabelMessageCode:'nav.reports.MarginCall.ValuationDate.yAxis', reportLabelFields:['valuationDate'],chartType:'column'),
		new Report(titleMessageCode:'nav.reports.MarginCall.ValuationDate', subtitleMessageCode:'nav.reports.MarginCall.ValuationDate.subtitle', xAxisLabelMessageCode:'nav.reports.MarginCall.ValuationDate.xAxis', yAxisLabelMessageCode:'nav.reports.MarginCall.ValuationDate.yAxis', reportLabelFields:['valuationDate'],chartType:'pie'),
		new Report(titleMessageCode:'nav.reports.MarginCall.ValuationDate', subtitleMessageCode:'nav.reports.MarginCall.ValuationDate.subtitle', xAxisLabelMessageCode:'nav.reports.MarginCall.ValuationDate.xAxis', yAxisLabelMessageCode:'nav.reports.MarginCall.ValuationDate.yAxis', reportLabelFields:['valuationDate'],chartType:'bar')]
	static def primaryFields = ['direction','businessState','marginAgreement','totalCallAmount','type','valuationDate','settlementDate'];
	static def secondaryFields=null
	static def defaultLabelProp = 'totalCallAmount';
	
	static def actions = [
		new Action(messageCode:Action.CREATE_LABEL,path:Action.CREATE_PATH,imagePath:'/images/app/marginCall/action/create.png',excludeFields: ['dateCreated','lastUpdated','direction','businessState','agreedAmount','counterpartyCollateralBalance','counterpartyInitialMargin','counterpartyMarkToMarket','cancelComments','rejectComments','disputedAmount','disputeComments','partyAExternalReference','partyBExternalReference']),
		new Action(messageCode:'action.agree',path:'/agree',imagePath:'/images/app/marginCall/action/agree.png',showMap:['businessState':[MarginCallBusinessState.RECEIVED.state]],includeFields: ['agreedAmount']),
		new Action(messageCode:'action.dispute',path:'/dispute',imagePath:'/images/app/marginCall/action/dispute.png',showMap:['businessState':[MarginCallBusinessState.RECEIVED.state]],includeFields: ['disputedAmount','disputeComments']),
		new Action(messageCode:'action.margincall.disputecancel',path:'/disputeCancel',imagePath:'/images/app/marginCall/action/dispute_cancel.png',showMap:['businessState':[MarginCallBusinessState.DISPUTED.state]],includeFields: []),
		new Action(messageCode:'action.cancel',path:'/cancel',imagePath:'/images/app/marginCall/action/cancel.png',showMap:[:],includeFields: ['cancelComments']),
		new Action(messageCode:'action.margincall.pledge',path:'/pledge',imagePath:'/images/app/marginCall/action/pledge.png',showMap:['businessState':[MarginCallBusinessState.RECEIVED,MarginCallBusinessState.AGREED.state]],excludeFields: ['marginAgreementShortName','dateCreated','lastUpdated','direction','businessState','agreedAmount','counterpartyCollateralBalance','counterpartyInitialMargin','counterpartyMarkToMarket','disputedAmount','cancelComments','disputeComments','partyAExternalReference','partyBExternalReference']),
		new Action(messageCode:'action.margincall.pledgecancel',path:'/pledgeCancel',imagePath:'/images/app/marginCall/action/pledge_cancel.png',showMap:['businessState':[MarginCallBusinessState.PLEDGED.state]],includeFields:['cancelComments']),
		new Action(messageCode:'action.margincall.pledgereject',path:'/pledgeReject',imagePath:'/images/app/marginCall/action/pledge_reject.png',showMap:['businessState':[MarginCallBusinessState.PLEDGED.state]],includeFields:['rejectComments']),
		new Action(messageCode:'action.margincall.pledgeamend',path:'/pledgeAmend',imagePath:'/images/app/marginCall/action/pledge_amend.png',showMap:['businessState':[MarginCallBusinessState.PLEDGED.state,MarginCallBusinessState.PLEDGE_REJECTED.state]],excludeFields: ['marginAgreementShortName','dateCreated','lastUpdated','direction','businessState','agreedAmount','counterpartyCollateralBalance','counterpartyInitialMargin','counterpartyMarkToMarket','disputedAmount','cancelComments','disputeComments','partyAExternalReference','partyBExternalReference']),
		new Action(messageCode:'action.margincall.pledgeaccept',path:'/pledgeAccept',imagePath:'/images/app/marginCall/action/pledge_accept.png',showMap:['businessState':[MarginCallBusinessState.PLEDGED.state]],includeFields: [])
	];

	@Transactional
	def create() {
		log.error("create called with JSON:"+request.JSON)
		def jsonObject = request.JSON
		MarginCall marginCall = new MarginCall(jsonObject)
		marginCall.businessState=MarginCallBusinessState.RECEIVED.state;
		save(marginCall)
	}
	
	def agree() {
		params.businessState=MarginCallBusinessState.AGREED.state;
		update(params.id,params.version);
	}
	
	def dispute() {
		params.businessState=MarginCallBusinessState.DISPUTED.state;
		update(params.id,params.version);
	}
	
	def disputeCancel() {
		log.warn("disputeCancel called with:"+params);
		params.businessState=MarginCallBusinessState.RECEIVED.state;
		update(params.id,params.version);
	}
	
	def cancel() {
		params.businessState=MarginCallBusinessState.CANCELLED.state;
		update(params.id,params.version);
	}
	
	def pledge() {
		params.businessState=MarginCallBusinessState.PLEDGED.state;
		update(params.id,params.version);
	}
	
	def pledgeCancel() {
		params.businessState=MarginCallBusinessState.AGREED.state;
		update(params.id,params.version);
	}
	
	def pledgeReject() {
		params.businessState=MarginCallBusinessState.PLEDGE_REJECTED.state;
		update(params.id,params.version);
	}
	
	def pledgeAmend() {
		params.businessState=MarginCallBusinessState.PLEDGED.state;
		update(params.id,params.version);
	}
	
	def pledgeAccept() {
		params.businessState=MarginCallBusinessState.PLEDGE_ACCEPTED.state;
		update(params.id,params.version);
	}

	//Ignore me
	static def navOrder = navAccordionHeader.order + "-" +navAccordionSubHeader.order

}
