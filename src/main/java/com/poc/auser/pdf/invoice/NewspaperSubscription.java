package com.poc.auser.pdf.invoice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewspaperSubscription {
	private String newspaper;
	private String language;
	private String state;
	private String district;
    private String mandal;
    private String scheduledTime;
    private double monthlySubscription;
}
