package com.poc.master.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "SUBSCRIPTION_TYPE")
@Data
public class SubscriptionType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriptiontypeid")
    private Long subscriptionTypeId;

    @Column(name = "subscriptiontype", columnDefinition = "VARCHAR2(10)")
    private String subscriptionType;

    @Column(name = "subscriptionduration", columnDefinition = "VARCHAR2(10)")
    private String subscriptionDuration;

    @Column(name = "subscriptionfee", columnDefinition = "DECIMAL(10, 2)")
    private Double subscriptionFee;
}