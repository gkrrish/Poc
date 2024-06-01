package com.poc.entity;

import java.sql.Timestamp;

import com.poc.master.entity.Vendor;

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
@Table(name = "NEWSPAPER_FILES")
public class NewspaperFiles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "file_id")
	private Long fileId;

	@Column(name = "newspaper_id")
	private Long newspaperId;

	@Column(name = "file_location", length = 512)
	private String fileLocation;

	@Column(name = "upload_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp uploadDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "newspaper_id", referencedColumnName = "newspaper_id", insertable = false, updatable = false)
	private Vendor vendor;
}
