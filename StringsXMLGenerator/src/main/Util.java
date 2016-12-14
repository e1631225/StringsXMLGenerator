package main;

import org.apache.commons.lang3.StringUtils;

public class Util {
	
	public static String splitXmlResult(String word) {
		// translate result returns like
		// <string xmlns="http://schemas.microsoft.com/2003/10/Serialization/">Katalogus</string>
		int beginIndex = StringUtils.indexOf(word, "\">") + 2;
		int endIndex = StringUtils.indexOf(word, "</string");
		try {
			return word.substring(beginIndex, endIndex).toLowerCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String exchangeProblematicCountryCode(String code) {
		// there are some country codes that are not same with google.
		// we exchange these country codes with google equivalents
		String result = code;
		
		if (code.equals("he")) {
			result = "iw";
		} else if (code.equals("id")) {
			result = "in";
		} else if (code.equals("mww")) { 
			result = "hmn";
		} else if (code.equals("sr-Cyrl")) {
			result = "sr";
		} else if (code.equals("yua")) {
			result = "myn";
		} else if (code.equals("zh-Hans")) {
			result = "zh";
		}
		return result;
	}
	
	public static String getXmlFormattedLine(String word, String tag) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\t");
		buffer.append("<string name=\"");
		buffer.append(tag);
		buffer.append("\">");
		buffer.append(word);
		buffer.append("</string>");
		return buffer.toString();
	}

}
