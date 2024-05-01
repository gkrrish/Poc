package com.poc.master.entity;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MASTER_INDIAN_NEWSPAPER_LANGUAGES")
public class IndianNewspaperLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int languageId;
	private String languageName;

	public IndianNewspaperLanguage() {
	}

	public IndianNewspaperLanguage(int languageId, String languageName) {
		this.languageId = languageId;
		this.languageName = languageName;
	}

	public int getLanguageId() {
		return languageId;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageId(int languageId) {
		this.languageId = languageId;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	@Override
	public String toString() {
		return "IndianNewspaperLanguage [languageId=" + languageId + ", languageName=" + languageName + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(languageId, languageName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndianNewspaperLanguage other = (IndianNewspaperLanguage) obj;
		return languageId == other.languageId && Objects.equals(languageName, other.languageName);
	}

}
