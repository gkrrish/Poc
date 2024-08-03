package com.poc.response;

import java.util.Base64;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class WelcomeBackPDFResponse extends WelcomeResponse {
    private String invoiceBase64;
    private String delta;

    public WelcomeBackPDFResponse(String message) {
        super(message);
    }

    public void setInvoiceBase64(byte[] invoice) {
        this.invoiceBase64 = Base64.getEncoder().encodeToString(invoice);
    }

    public void setDelta(String delta) {
        this.delta = delta;
    }
}
