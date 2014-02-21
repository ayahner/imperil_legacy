package org.example

import org.joda.time.LocalDate


class Pledge {
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
	Integer[] cancelReasonCodes;
	Integer[] rejectReasonCodes;
	String cancelReasonComments;
	String comments;
	String counterpartyAExternalReference;
	String counterpartyBExternalReference;
	String deliveryType;
	String externalReference;
	String fxCurrency;
	String recallItemAmpId;
	String rejectComments;
	String securityId;
	String securityIdType;
	String ssi;
	String temporaryExternalReference;
}
