package org.example.ext

import org.joda.time.LocalDate;

import com.dynamix.util.NavigationUI

public enum InterestStatementBusinessState {
	RECEIVED('Received'),
	UNPAIRED('Unpaired'),
	MISMATCHED('Mismatched'),
	MATCHED('Matched'),
	PENDING_VALUE_DATE('Pending Value Date'),
	MATCHED_FINAL('Matched Final'),
	PAIRED('Paired'),
	CANCELLED('Cancelled')

	private InterestStatementBusinessState(String state) { this.state = state }
	final String state
 
	static InterestStatementBusinessState byState(String state) {
	   values().find { it.state == state }
	}
 }