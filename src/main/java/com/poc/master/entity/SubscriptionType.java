package com.poc.master.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "SUBSCRIPTION_TYPE")
@Data
public class SubscriptionType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscriptiontypeid")
	private Integer subscriptionTypeId;

	@Column(name = "subscriptiontype", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private SubscriptionTypeEnum subscriptionType;

	@Column(name = "subscriptionduration", nullable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private SubscriptionDurationEnum subscriptionDuration;

	@Column(name = "subscriptionfee", nullable = false, precision = 10, scale = 2)
	private BigDecimal subscriptionFee;

	public enum SubscriptionTypeEnum {
		FREE, PAID
	}

	public enum SubscriptionDurationEnum {
		ONE_MONTH("1 MONTH"), THREE_MONTHS("3 MONTHS"), SIX_MONTHS("6 MONTHS"), TWELVE_MONTHS("12 MONTHS");

		private final String value;

		SubscriptionDurationEnum(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}

		public static SubscriptionDurationEnum fromValue(String value) {
			for (SubscriptionDurationEnum duration : values()) {
				if (duration.value.equals(value)) {
					return duration;
				}
			}
			throw new IllegalArgumentException("Unknown subscription duration: " + value);
		}
	}
}
