package com.poc.request;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.poc.request.WelcomeRequest;

public class WelcomeRequestTest {
	
	private static final String INDIAN_MOBILE_NUMBER_PREFIX = "+91";

	@Test
	@DisplayName("Valid Indian Mobile Number")
	void testValidateMobileNumber_ValidNumber_IndianNumbers() {
		
		String input = INDIAN_MOBILE_NUMBER_PREFIX + "9876543210";
		WelcomeRequest welcomeRequest = new WelcomeRequest("Hi", input);
		boolean isValid = isValidMobileNumberFormat(welcomeRequest.getMobileNumber());
		assertTrue(isValid);
	}

	@ParameterizedTest
	@DisplayName("Invalid Mobile Number Formats")
	@ValueSource(strings = { 
								"+91-9876543210",
								"9876543210",
								"+449876543210",
								"+9198765" }
	)
	void testValidateMobileNumber_InvalidFormats(String input) {
		WelcomeRequest welcomeRequest = new WelcomeRequest("Hi", input);
		boolean isValid = isValidMobileNumberFormat(welcomeRequest.getMobileNumber());
		assertFalse(isValid, "Mobile number format should be invalid: " + input);
	}
	
	@ParameterizedTest
	@DisplayName("Null and Empty Inputs")
	@ValueSource(strings = { "", " " })
	void testValidateMobileNumber_NullOrEmptyInput(String input) {
		WelcomeRequest welcomeRequest = new WelcomeRequest("Hi", input);
		boolean isValid = isValidMobileNumberFormat(welcomeRequest.getMobileNumber());
		assertFalse(isValid);
	}

	
	private boolean isValidMobileNumberFormat(String mobileNumber) {
        return mobileNumber.matches("^\\+91[1-9]\\d{9}$");
    }

}
