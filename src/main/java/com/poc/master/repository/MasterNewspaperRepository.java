package com.poc.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.MasterNewspaper;


@Repository
public interface MasterNewspaperRepository extends JpaRepository<MasterNewspaper, Long> {
	
	@Query("SELECT DISTINCT mn.id, mn.newspaperName " +
	           "FROM MasterNewspaper mn " +
	           "JOIN Vendor v ON mn = v.newspaperMaster " +
	           "JOIN StatewiseLocation msl ON v.location = msl " +
	           "JOIN State ms ON msl.state = ms " +
	           "WHERE ms.stateName = :stateName")
	    List<Object[]> findDistinctNewspapersByStateName(@Param("stateName") String stateName);
}
