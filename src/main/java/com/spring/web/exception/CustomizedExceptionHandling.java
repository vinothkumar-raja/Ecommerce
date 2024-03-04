package com.spring.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomizedExceptionHandling extends ResponseEntityExceptionHandler{
	 
	@ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Object> handleExceptions(InvalidInputException ex, WebRequest webRequest) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
        return entity;
    }
	
	@ExceptionHandler(NoDataException.class)
    public ResponseEntity<Object> handleExceptions(NoDataException ex, WebRequest webRequest) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(ex.getMessage());
        ResponseEntity<Object> entity = new ResponseEntity<>(response, HttpStatus.OK);
        return entity;
    }
}