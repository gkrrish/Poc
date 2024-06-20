package com.poc.request;

import java.util.Date;

import lombok.Data;

@Data
public class UserSubscriptionDTO {
	private Long userId;
	private Long newspaperMasterId;
	private Long subscriptionTypeId;
	private Date subscriptionStartDate;
	private Date subscriptionEndDate;
	public Long statewiselocationId;
}
