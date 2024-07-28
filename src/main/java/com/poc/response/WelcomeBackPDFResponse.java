package com.poc.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WelcomeBackPDFResponse extends WelcomeResponse {
    private byte[] invoice;

    public WelcomeBackPDFResponse(String message) {
        super(message);
    }
}
