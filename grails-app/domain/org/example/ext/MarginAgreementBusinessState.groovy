package org.example.ext

import com.dynamix.util.NavigationUI;

public enum MarginAgreementBusinessState {
	PENDING_NEW('Pending New'),
	PENDING_REJECTED('Pending Rejected'),
	PENDING_CHANGE('Pending Change'),
	PENDING_DISCONTINUATION('Pending Discontinuation'),
	ACTIVE('Active'),
	CANCELLED('Cancelled')

	private MarginAgreementBusinessState(String state) {
		this.state = state
	}
	final String state

	static MarginAgreementBusinessState byState(String state) {
		values().find { it.state == state }
	}
} 
