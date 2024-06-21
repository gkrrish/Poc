package com.poc.auser.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.auser.master.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
	List<State> findAllByCountryId(Integer countryId);
	
	@Query("SELECT DISTINCT ms.stateId, ms.stateName " +
	           "FROM MasterNewspaper mn " +
	           "JOIN Vendor v ON mn.id = v.newspaperMaster.id " +
	           "JOIN StatewiseLocation msl ON v.location.locationId = msl.locationId " +
	           "JOIN State ms ON msl.state.stateId = ms.stateId " +
	           "WHERE mn.newspaperName = :newspaperName")
	    List<Object[]> findDistinctStatesByNewspaperName(@Param("newspaperName") String newspaperName);
}
