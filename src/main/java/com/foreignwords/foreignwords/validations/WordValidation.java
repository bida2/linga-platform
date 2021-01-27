package com.foreignwords.foreignwords.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordValidation {
	// Checks if string contains only Bulgarian letters + the special characters "," ";" and "!" and whitespace characters
	public static Boolean containsOnlyBgPlusSpecialChars(String testString) {
		Pattern pattern = Pattern.compile("[\\p{IsCyrillic}]+[\\s,;!]*$");
	    Matcher matcher = pattern.matcher(testString);
	    return matcher.find();
	}
}
