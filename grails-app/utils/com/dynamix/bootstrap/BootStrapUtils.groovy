package com.dynamix.bootstrap

import org.apache.commons.lang.time.DateUtils
import org.joda.time.LocalDate

class BootStrapUtils {
	static Random random = new Random();

	public static int getRandomIntForDate(){
		return 30 - random.nextInt(60);
	}

	public static java.sql.Date getRandomSqlDate(){
		return new java.sql.Date(DateUtils.addDays(new Date(),getRandomIntForDate()).getTime());
	}

	public static java.util.Date getRandomUtilDate(){
		return DateUtils.addDays(new Date(),getRandomIntForDate()).clearTime();
	}

	public static LocalDate getRandomLocalDate(){
		return new LocalDate(DateUtils.addDays(new Date(),getRandomIntForDate()).getTime());
	}
}
