package com.poc.pdf.invoice;

import java.util.Date;
import java.util.List;

import com.poc.response.UserDetailsResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
	private String invoiceTo;
    private String mobileNumber;
    private String email;
    private String contactDetails;
    private Date date;
    private double totalDue;
    private Long invoiceNo;
//    private List<NewspaperSubscription> subscriptions;
    private List<UserDetailsResponse> subscriptions;
    private PaymentDetails paymentDetails;

}
