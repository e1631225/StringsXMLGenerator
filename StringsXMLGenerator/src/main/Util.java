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

}
