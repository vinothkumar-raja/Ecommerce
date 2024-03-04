package com.spring.web.exception;

public class InvalidInputException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidInputException(String msg) {
		super(msg);
	}

}
