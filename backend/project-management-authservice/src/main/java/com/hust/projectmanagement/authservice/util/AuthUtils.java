package com.hust.projectmanagement.authservice.util;

public class AuthUtils {
	public static boolean validatePassword(String password) {
		if (password != null && password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
			return true;
		}
		return false;
	}
}
