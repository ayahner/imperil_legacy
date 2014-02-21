package com.dynamix.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Date tryToParseDate(String date) {
		try {
			return new SimpleDateFormat("dd MMM yyyy").parse(date);
		} catch (ParseException e) {
			try {
				return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
						.parse(date);
			} catch (ParseException e2) {
				return null;
			}
		}
	}
}
