package com.poc.exceptions;

public class UserDetailsNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserDetailsNotFoundException(String message) {
		super(message);
	}
}
