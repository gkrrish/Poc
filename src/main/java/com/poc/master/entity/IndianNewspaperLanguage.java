package com.poc.master.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "MASTER_INDIAN_NEWSPAPER_LANGUAGES")
@Data
public class IndianNewspaperLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int languageId;
	private String languageName;
}
