package com.lms.util;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseStructure<T> {

	private String message;
	private int statusCode;
	private T data;
}
