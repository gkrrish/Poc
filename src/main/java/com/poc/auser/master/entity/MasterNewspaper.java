package com.poc.auser.master.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "MASTER_NEWSPAPER")
@Data
public class MasterNewspaper {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "newspaper_master_id")
	private Long id;

	@Column(name = "newspaper_name", length = 100, unique = true)
	private String newspaperName;

	@ManyToOne
	@JoinColumn(name = "vendor_id", referencedColumnName = "vendorid")
	private VendorDetails vendor;
}
