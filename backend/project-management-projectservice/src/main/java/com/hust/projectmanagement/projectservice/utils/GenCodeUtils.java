package com.hust.projectmanagement.projectservice.utils;

import org.apache.commons.lang.RandomStringUtils;

public class GenCodeUtils {
	public static String OTP(int len) {
		  // RandomStringUtils.random(len, "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
		  String numbers = "0123456789#$&*!";
		  String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	  return RandomStringUtils.random(8, numbers+alpha);
	  }
}
