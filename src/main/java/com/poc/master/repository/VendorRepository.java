package com.poc.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.Vendor;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, Long> {
	
	@Query("SELECT DISTINCT ms.stateId, ms.stateName FROM Vendor v " +
	           "JOIN v.location msl " +
	           "JOIN msl.state ms " +
	           "JOIN v.newspaperLanguage mnl " +
	           "WHERE mnl.languageName = :languageName")
	    List<Object[]> findDistinctStatesByLanguage(@Param("languageName") String languageName);
}