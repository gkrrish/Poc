package com.poc.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExistingUserDetails {

	private String mobileNumber;
	private List<UserDetailsResponse> details;
	private double totalSubscriptionCharges;
	
}
