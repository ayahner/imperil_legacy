package com.dynamix.util;

import java.math.BigDecimal;

import org.codehaus.groovy.grails.commons.GrailsDomainClass;
import org.codehaus.groovy.grails.commons.GrailsDomainClassProperty;
import org.joda.time.LocalDate;

public class DomainUtils {

	public static Long safeLong(String str) {
		return str != null ? Long.valueOf(str) : null;
	}

	public static boolean isBoolean(Class<?> type) {
		return Boolean.class.equals(type) || boolean.class.equals(type);
	}

	public static boolean isDate(GrailsDomainClass domainClass, String propName) {
		return isUtilDate(domainClass, propName)
				|| isSqlDate(domainClass, propName)
				|| isJodaDate(domainClass, propName);
	}

	public static boolean isDate(Class<?> type) {
		return isUtilDate(type) || isSqlDate(type) || isJodaDate(type);
	}

	public static boolean isJodaOrSqlDate(GrailsDomainClass domainClass,
			String propName) {
		return isSqlDate(domainClass, propName)
				|| isJodaDate(domainClass, propName);
	}

	public static boolean isJodaOrSqlDate(Class<?> type) {
		return isSqlDate(type) || isJodaDate(type);
	}

	public static boolean isUtilDate(GrailsDomainClass domainClass,
			String propName) {
		if (domainClass.hasPersistentProperty(propName)) {
			GrailsDomainClassProperty persistentProp = domainClass
					.getPersistentProperty(propName);
			return isUtilDate(persistentProp.getType());
		} else {
			return false;
		}
	}

	public static boolean isSqlDate(GrailsDomainClass domainClass,
			String propName) {
		if (domainClass.hasPersistentProperty(propName)) {
			GrailsDomainClassProperty persistentProp = domainClass
					.getPersistentProperty(propName);
			return isSqlDate(persistentProp.getType());
		} else {
			return false;
		}
	}

	public static boolean isJodaDate(GrailsDomainClass domainClass,
			String propName) {
		if (domainClass.hasPersistentProperty(propName)) {
			GrailsDomainClassProperty persistentProp = domainClass
					.getPersistentProperty(propName);
			return isJodaDate(persistentProp.getType());
		} else {
			return false;
		}
	}

	public static boolean isUtilDate(Class<?> type) {
		return java.util.Date.class.equals(type);
	}

	public static boolean isSqlDate(Class<?> type) {
		return java.sql.Date.class.equals(type);
	}

	public static boolean isJodaDate(Class<?> type) {
		return LocalDate.class.equals(type);
	}

	public static boolean isNumber(Class<?> type) {
		return BigDecimal.class.equals(type);
	}
}
