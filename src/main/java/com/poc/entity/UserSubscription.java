package com.poc.entity;

import com.poc.master.entity.BatchJob;
import com.poc.master.entity.Vendor;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USER_SUBSCRIPTION")
@Data
public class UserSubscription {

	@EmbeddedId
	private UserSubscriptionId id;

	@ManyToOne
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private UserDetails userDetails;

	@ManyToOne
	@JoinColumn(name = "newspaper_id", insertable = false, updatable = false)
	private Vendor vendor;

	@ManyToOne
	@JoinColumn(name = "batch_id")
	private BatchJob batch;

}
