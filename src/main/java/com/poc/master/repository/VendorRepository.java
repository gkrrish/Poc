package com.poc.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.Vendor;
import com.poc.master.entity.VendorId;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, VendorId> {
	
	   @Query("SELECT DISTINCT ms.stateId, ms.stateName FROM Vendor v " +
	           "JOIN v.location msl " +
	           "JOIN msl.state ms " +
	           "JOIN v.newspaperLanguage mnl " +
	           "WHERE mnl.languageName = :languageName")
	   public List<Object[]> findDistinctStatesByLanguage(@Param("languageName") String languageName);
	    
	   @Query("SELECT v FROM Vendor v WHERE v.id.newspaperMasterId = :newspaperMasterId AND v.id.locationId = :locationId")
	   Optional<Vendor> findVendorsByMasterIdAndLocationId(@Param("newspaperMasterId") Long newspaperMasterId, @Param("locationId") Long locationId);

	   @Query("SELECT v FROM Vendor v WHERE v.id.newspaperId = :newspaperId")
	    List<Vendor> findByNewspaperId(@Param("newspaperId") Long newspaperId);
}