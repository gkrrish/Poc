package com.poc.my;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDetails {
	private String paymentMethod;
	private String bankName;
	private String bankAccount;

}
