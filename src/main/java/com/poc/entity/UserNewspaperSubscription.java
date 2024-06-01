package com.poc.entity;

import java.util.Date;

import com.poc.master.entity.MasterNewspaper;
import com.poc.master.entity.SubscriptionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USER_NEWSPAPER_SUBSCRIPTION")
public class UserNewspaperSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "newspaper_master_id")
    private Long newspaperMasterId;

    @Column(name = "subscriptiontypeid")
    private Long subscriptionTypeId;

    @Column(name = "subscription_start_date")
    private Date subscriptionStartDate;

    @Column(name = "subscription_end_date")
    private Date subscriptionEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "UserID", insertable = false, updatable = false)
    private UserDetails userDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "newspaper_master_id", referencedColumnName = "newspaper_master_id", insertable = false, updatable = false)
    private MasterNewspaper masterNewspaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriptiontypeid", referencedColumnName = "subscriptiontypeid", insertable = false, updatable = false)
    private SubscriptionType subscriptionType;
}
