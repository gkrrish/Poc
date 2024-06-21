package com.poc.auser.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.auser.master.entity.StatewiseLocation;

@Repository
public interface GenerateLocationNameRepository extends JpaRepository<StatewiseLocation, Long> {

	@Query(value = "SELECT generate_location_name(:state_id, :district_id, :mandal_id) FROM DUAL", nativeQuery = true)
    String generateLocationName(@Param("state_id") Integer stateId,
                                @Param("district_id") Integer districtId,
                                @Param("mandal_id") Integer mandalId);

}