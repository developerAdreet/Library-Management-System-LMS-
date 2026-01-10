package com.lms.exception;

public class UserIdNotFoundException extends RuntimeException{

public UserIdNotFoundException() {
		
	}

	public  UserIdNotFoundException(String message) {
		super(message);
	}
}
