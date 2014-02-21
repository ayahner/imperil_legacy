package com.dynamix.util;

import grails.util.GrailsNameUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NameUtils {

	
	/**
	 * Implemented similarly to {@code GrailsNameUtils.getNaturalName(String name)}. <br/>
	 * 
	 * Now better handles back to back capitals, e.g. PartyAShortName, see {@code NameUtilsTest}
	 * 
	 * @param name
	 * @return
	 */
	public static String getNaturalName(String name) {
		name = GrailsNameUtils.getShortName(name);
		List<String> words = new ArrayList<String>();
		int i = 0;
		char[] chars = name.toCharArray();
		for (int j = 0; j < chars.length; j++) {
			char c = chars[j];
			String w;
			if (i >= words.size()) {
				w = "";
				words.add(i, w);
			} else {
				w = words.get(i);
			}

			if (Character.isLowerCase(c) || Character.isDigit(c)) {
				if (Character.isLowerCase(c) && w.length() == 0) {
					c = Character.toUpperCase(c);
				} else if (w.length() > 1
						&& Character.isUpperCase(w.charAt(w.length() - 1))) {
					w = "";
					words.add(++i, w);
				}

				words.set(i, w + c);
			} else if (Character.isUpperCase(c)) {
				if ((i == 0 && w.length() == 0)) {
					words.set(i, w + c);
				} else {
					words.add(++i, String.valueOf(c));
				}
			}
		}

		StringBuilder buf = new StringBuilder();
		for (Iterator<String> j = words.iterator(); j.hasNext();) {
			String word = j.next();
			buf.append(word);
			if (j.hasNext()) {
				buf.append(' ');
			}
		}
		return buf.toString();
	}
}
