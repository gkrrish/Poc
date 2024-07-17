package com.poc.request;

import lombok.Data;

@Data
public class CreateVendorDetailsRequest {
	private String vendorName;
    private String vendorContactDetails;
    private String vendorStatus; // active or inactive
}
