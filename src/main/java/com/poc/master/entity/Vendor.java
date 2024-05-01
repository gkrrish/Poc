package com.poc.master.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "VENDORS")
@Data
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "newspaper_id")
	private Long id;

	@Column(name = "newspaper_name", length = 100)
	private String newspaperName;

	@ManyToOne
	@JoinColumn(name = "location_id", referencedColumnName = "location_id")
	private StatewiseLocation location;

	@ManyToOne
	@JoinColumn(name = "newspaper_language", referencedColumnName = "languageId")
	private IndianNewspaperLanguage newspaperLanguage;

	@ManyToOne
	@JoinColumn(name = "subscription_type_id", referencedColumnName = "subscriptiontypeid")
	private SubscriptionType subscriptionType;

	@Column(name = "publication_type", length = 10)
	private String publicationType;

	@ManyToOne
	@JoinColumn(name = "vendor_id", referencedColumnName = "vendorid")
	private VendorDetails vendor;

}
