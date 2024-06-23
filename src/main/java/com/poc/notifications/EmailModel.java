package com.poc.notifications;

import lombok.Data;

@Data
public class EmailModel {
	
	private String toEmailId;
	private String emailSubject;
	private String emailBody;
	private String fileAddress;
	private String fromEmailId;
}
