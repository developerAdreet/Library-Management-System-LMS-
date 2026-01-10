package com.lms.exception;

public class BookIdNotFoundException extends RuntimeException{

public BookIdNotFoundException() {
		
	}

	public  BookIdNotFoundException(String message) {
		super(message);
	}
}
