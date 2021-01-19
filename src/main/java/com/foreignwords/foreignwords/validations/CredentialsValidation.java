package com.foreignwords.foreignwords.validations;

import java.sql.Date;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.foreignwords.foreignwords.entities.PasswordResetToken;

public class CredentialsValidation {
	
	public static Boolean isEmailValid(String email) {
		Pattern pattern = Pattern.compile("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
	    Matcher matcher = pattern.matcher(email);
	    return matcher.find();
	}
	
	public static Boolean isUsernameValid(String username) {
		// Covers all Latin letters, numbers and the _ character
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(username);
		return matcher.find();
	}
	
	public static Boolean isPasswordValid(String password) {
		Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$#!%*?&])[A-Za-z\\d@$#!%*?&]{8,}$");
	    Matcher matcher = pattern.matcher(password);
	    return matcher.find();
	}
	
	public static Boolean isParameterNullOrEmpty(Object param) {
		if (param.equals(null) || param.toString().isEmpty())
			return true;
		else return false;
	}
	
	public static Boolean doPasswordsMatch(String pass, String passConfirm) {
		if (!passConfirm.equals(null) && passConfirm.equals(pass))
			return true;
		else return false;
	}
	
	public static Boolean hasPassResetTokenExpired(PasswordResetToken token) {
		if (Date.valueOf(LocalDate.now()).after(token.getExpiryDate()))
			return true;
		else return false;
	}
}
