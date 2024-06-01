package com.poc.customeinvoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetails {
    private String billingName;
    private String billingAddress;
    private String billingMobileNumber;
    private String nowAddress;
}
