package org.example

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.example.ext.SubstitutionBusinessState;
import org.joda.time.LocalDate;

import com.dynamix.util.NavigationUI;
import com.dynamix.util.Report;

class Substitution {
	static belongsTo = [marginAgreement: MarginAgreement]
	static hasMany = []
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
	String businessState=SubstitutionBusinessState.RECEIVED.state;
	String cancelComments;
	String comments;
	String currency='USD';
	String direction='Outgoing';
	String disputeComments;
	String externalUsername;
	String partyAExternalReference;
	String partyBExternalReference;
	String rejectComments;
	String type;

	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = SubstitutionController.navStates
	static def reports = SubstitutionController.reports
	static def primaryFields = SubstitutionController.primaryFields;
	static def secondaryFields = SubstitutionController.secondaryFields;
	static def actions = SubstitutionController.actions;
	static def defaultLabelProp = SubstitutionController.defaultLabelProp;
}
