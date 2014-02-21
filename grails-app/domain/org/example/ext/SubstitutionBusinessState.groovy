package org.example.ext

import java.util.Date;

import org.joda.time.LocalDate;

import com.dynamix.util.NavigationUI;
import com.dynamix.util.Report;

public enum SubstitutionBusinessState {
	RECEIVED('Received'),
	ACCEPTED('Accepted'),
	REJECTED('Rejected'),
	CANCELLED('Cancelled')

	final String state

	private SubstitutionBusinessState(String state) {
		this.state = state
	}

	static SubstitutionBusinessState byState(String state) {
		values().find { it.state == state }
	}
}
