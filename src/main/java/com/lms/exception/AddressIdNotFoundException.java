package com.lms.exception;



public class AddressIdNotFoundException extends RuntimeException{
	
	public AddressIdNotFoundException() {
		
	}

	public  AddressIdNotFoundException(String message) {
		super(message);
	}
}
