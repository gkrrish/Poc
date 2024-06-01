package com.poc.master.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "VENDORS")
@Data
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newspaper_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private StatewiseLocation location;

    @ManyToOne
    @JoinColumn(name = "newspaper_master_id", referencedColumnName = "newspaper_master_id")
    private MasterNewspaper newspaperMaster;

    @ManyToOne
    @JoinColumn(name = "newspaper_language", referencedColumnName = "language_id")
    private IndianNewspaperLanguage newspaperLanguage;

    @ManyToOne
    @JoinColumn(name = "subscription_type_id", referencedColumnName = "subscriptiontypeid")
    private SubscriptionType subscriptionType;

    @Column(name = "publication_type", length = 10)
    private String publicationType;

    @Column(name = "category_id")
    private Long categoryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", insertable = false, updatable = false)
    private CategoryType category;
}
