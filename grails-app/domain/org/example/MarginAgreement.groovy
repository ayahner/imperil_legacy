package org.example

import org.example.ext.MarginAgreementBusinessState;
import org.joda.time.LocalDate;

import com.dynamix.util.NavigationUI
import com.dynamix.util.Report;

class MarginAgreement {
	static belongsTo = [partyA: Party, partyB: Party]
	static hasMany = [marginCalls: MarginCall,substitutions: Substitution,interestStatement:InterestStatement]
	static constraints = {
		longName(blank:false)
	}
	static mapping = {
		sort lastUpdated: "desc"
    }	

	Date dateCreated
	Date lastUpdated
	Long version

	BigDecimal threshold;
	Boolean allowMultipleCallsPerDay=true;
	Boolean interestStatementEnabled=false;
	Boolean marginCallEnabled=true;
	Boolean soleCalculation=false;
	Boolean substitutionEnabled=false;
	LocalDate activeDate = new LocalDate(new Date())
	LocalDate discontinueDate
	String acceptedBy;
	String cancelComments;
	String disclaimer;
	String shortName;
	String type='ISDA';
	String businessState='Pending New';
	String createdBy;
	String description;
	String direction='Outgoing';
	String externalReference;
	Party lastModifiedParty;
	String longName;
	String rejectComments;
	String rejectedBy;
	String timeZone='GMT';
	String valuationAgent;
	
	//These are here b/c the Controller template cannot refer the Controller instance directly
	static def navStates = MarginAgreementController.navStates
	static def reports = MarginAgreementController.reports
	static def primaryFields = MarginAgreementController.primaryFields;
	static def secondaryFields = MarginAgreementController.secondaryFields;
	static def actions = MarginAgreementController.actions;
	static def defaultLabelProp = MarginAgreementController.defaultLabelProp;
}
