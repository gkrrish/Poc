package com.poc.master.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "VENDOR_DETAILS")
@Data
public class VendorDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vendorid")
	private Long vendorId;

	@Column(name = "vendorname", length = 255)
	private String vendorName;

	@Column(name = "newspaper_language")
	private Integer newspaperLanguage;

	@Column(name = "vendorcontactdetails", length = 512)
	private String vendorContactDetails;

	@Column(name = "vendorstatus", length = 10)
	private String vendorStatus;
}
