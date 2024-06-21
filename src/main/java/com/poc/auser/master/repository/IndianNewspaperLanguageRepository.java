package com.poc.auser.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poc.auser.master.entity.IndianNewspaperLanguage;

@Repository
public interface IndianNewspaperLanguageRepository extends JpaRepository<IndianNewspaperLanguage, Integer> {

	@Query("SELECT language.languageName FROM IndianNewspaperLanguage language")
	List<String> findAllLanguageNames();
	

}