package org.example.ext
 
import java.util.Date;

import org.joda.time.LocalDate;

import com.dynamix.util.NavigationUI;
import com.dynamix.util.Report;

public enum MarginCallBusinessState {
	RECEIVED('Received'),
	AGREED('Agreed'),
	DISPUTED('Disputed'),
	PLEDGED('Pledged'),
	PLEDGE_ACCEPTED('Pledge Accepted'),
	PLEDGE_REJECTED('Pledge Rejected'),
	CANCELLED('Cancelled')

	final String state

	private MarginCallBusinessState(String state) {
		this.state = state
	}

	static MarginCallBusinessState byState(String state) {
		values().find { it.state == state }
	}
}
