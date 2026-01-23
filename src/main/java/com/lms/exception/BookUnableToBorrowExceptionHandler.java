package com.lms.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lms.util.ResponseStructure;

@ControllerAdvice
public class BookUnableToBorrowExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllOtherException(Exception ex){
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Other exception");
		responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
		responseStructure.setData(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(responseStructure);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Request method not supported");
		responseStructure.setStatusCode(status.value());
		responseStructure.setData(ex.getMessage());
		return ResponseEntity.status(status).body(responseStructure);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Missing path variable");
		responseStructure.setStatusCode(status.value());
		responseStructure.setData(ex.getMessage());
		return ResponseEntity.status(status).body(responseStructure);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Missing data for request parameter");
		responseStructure.setStatusCode(status.value());
		responseStructure.setData(ex.getMessage());
		return ResponseEntity.status(status).body(responseStructure);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatusCode status, WebRequest request) {
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Data type mismatch");
		responseStructure.setStatusCode(status.value());
		responseStructure.setData(ex.getMessage());
		return ResponseEntity.status(status).body(responseStructure);
	}

	
	@ExceptionHandler(BookUnableToBorrowException.class)
	public ResponseEntity<ResponseStructure<String>> handleBookIdNotFoundException(BookUnableToBorrowException ex){
		ResponseStructure<String> responseStructure = new ResponseStructure<>();
		responseStructure.setMessage("Book not found for this id!");
		responseStructure.setStatusCode(HttpStatus.BAD_REQUEST.value());
	responseStructure.setData(ex.getMessage());
	return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.NOT_FOUND);
	}
}

	
