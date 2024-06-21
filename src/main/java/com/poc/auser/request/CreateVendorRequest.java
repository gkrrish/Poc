package com.poc.auser.request;

import lombok.Data;

@Data
public class CreateVendorRequest {
	private Long newspaperId;
    private Long locationId;
    private Long newspaperMasterId;
    private Long newspaperLanguageId;
    private Long subscriptionTypeId;
    private String publicationType;
    private Long categoryId;
}
