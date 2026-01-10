package com.lms.exception;

public class LibraryIdNotFoundException extends RuntimeException{

	public LibraryIdNotFoundException() {
		
	}
	
	public LibraryIdNotFoundException(String message) {
		super(message);
	}
}
