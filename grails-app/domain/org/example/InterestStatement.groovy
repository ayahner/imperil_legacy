package org.example

import org.apache.commons.lang.time.DateUtils
import org.example.ext.InterestStatementBusinessState
import org.joda.time.LocalDate

import com.dynamix.util.NavigationUI
import com.dynamix.util.Report


class InterestStatement {
	static belongsTo = [marginAgreement: MarginAgreement]
	static constraints = {
	}
	static mapping = {
		sort lastUpdated: "desc"
    }
	
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
	String businessState=InterestStatementBusinessState.RECEIVED.state;
	String cancelComments;
	String comments;
	String currency='USD';
	String direction='Outgoing';
	String disputeComments;
	String externalUsername;
	String partyAExternalReference;
	String partyBExternalReference;
	String type;

	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = InterestStatementController.navStates
	static def reports = InterestStatementController.reports
	static def primaryFields = InterestStatementController.primaryFields;
	static def secondaryFields = InterestStatementController.secondaryFields;
	static def actions = InterestStatementController.actions;
	static def defaultLabelProp = InterestStatementController.defaultLabelProp;
}
