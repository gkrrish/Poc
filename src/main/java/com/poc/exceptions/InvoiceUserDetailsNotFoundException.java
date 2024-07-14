package com.poc.exceptions;

public class InvoiceUserDetailsNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvoiceUserDetailsNotFoundException(String message) {
		super(message);
	}
}