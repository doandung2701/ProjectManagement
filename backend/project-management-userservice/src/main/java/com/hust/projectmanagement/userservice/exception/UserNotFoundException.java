package com.hust.projectmanagement.userservice.exception;

public class UserNotFoundException extends RuntimeException{
	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public UserNotFoundException(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}
}
