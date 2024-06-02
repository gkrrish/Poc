package com.poc.my;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {
	private String invoiceTo;
    private String mobileNumber;
    private String email;
    private String contactDetails;
    private String date;
    private double totalDue;
    private String invoiceNo;
    private List<NewspaperSubscription> subscriptions;
    private PaymentDetails paymentDetails;

}
