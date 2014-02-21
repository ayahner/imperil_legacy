package org.example

import org.apache.commons.lang.time.DateUtils
import org.example.ext.MarginCallBusinessState
import org.joda.time.LocalDate

class MarginCall {
	static belongsTo = [marginAgreement: MarginAgreement]
	static hasMany = [pledges: Pledge, recallItems: RecallItem]
	static constraints = {
	}
	static mapping = { sort lastUpdated: "desc" }

	Date dateCreated
	Date lastUpdated
	Long version

	BigDecimal agreedAmount;
	BigDecimal collateralValue;
	BigDecimal counterpartyCollateralBalance;
	BigDecimal counterpartyInitialMargin;
	BigDecimal counterpartyMarkToMarket;
	BigDecimal deliverAmount;
	BigDecimal deliverMinimumTransferAmount;
	BigDecimal deliverRoundingAmount;
	BigDecimal disputedAmount;
	BigDecimal exposure;
	BigDecimal interestAmount;
	BigDecimal minimumTransferAmount;
	BigDecimal netRequiredAmount;
	BigDecimal pendingCollateral;
	BigDecimal returnAmount;
	BigDecimal returnMinimumTransferAmount;
	BigDecimal returnRoundingAmount;
	BigDecimal roundingAmount;
	BigDecimal threshold;
	BigDecimal totalCallAmount;
	LocalDate settlementDate= new LocalDate(DateUtils.addDays(new Date(), -1));
	LocalDate valuationDate = new LocalDate(new Date());
	String businessState=MarginCallBusinessState.RECEIVED.state;
	String cancelComments;
	String comments;
	String currency='USD';
	String direction='Outgoing';
	String disputeComments;
	String rejectComments;
	String externalUsername;
	String partyAExternalReference;
	String partyBExternalReference;
	String type='CSA';
	
	String getMarginAgreementShortName(){
		return marginAgreement?.shortName;
	}
	void setMarginAgreementShortName(String s){
	}

	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = MarginCallController.navStates
	static def reports = MarginCallController.reports
	static def primaryFields = MarginCallController.primaryFields;
	static def secondaryFields = MarginCallController.secondaryFields;
	static def actions = MarginCallController.actions;
	static def defaultLabelProp = MarginCallController.defaultLabelProp;
}
