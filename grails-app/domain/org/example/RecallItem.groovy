package org.example

import java.util.Date;

import org.joda.time.LocalDate;

class RecallItem {
	static belongsTo = [marginCall: MarginCall]

	static def primaryFields = ['settlementDate','securityId','securityIdType','finalTransferAmount','fxRate','haircut','price','currentMarketValue','adjustedCollateralValue'];
	
	Date dateCreated
	Date lastUpdated
	Long version
	
	BigDecimal adjustedCollateralValue;
	BigDecimal currentMarketValue;
	BigDecimal finalTransferAmount;
	BigDecimal fxRate;
	BigDecimal haircut;
	BigDecimal price;
	BigDecimal quantity;
	LocalDate settlementDate;
	Integer[] rejectReasonCodes;
	String businessState;
	String comments;
	String counterpartyAExternalReference;
	String counterpartyBExternalReference;
	String externalReference;
	String fxCurrency;
	String marginCallAmpId;
	String rejectComments;
	String securityId;
	String securityIdType;
	String temporaryExternalReference;
}
