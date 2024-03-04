package com.spring.web.exception;

public class NoDataException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public NoDataException(String msg) {
		super(msg);
	}

}
