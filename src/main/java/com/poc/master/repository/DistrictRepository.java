package com.poc.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
	
	@Query("SELECT d.districtName FROM District d JOIN d.state s WHERE s.stateName = :stateName")
    List<String> findAllDistrictNamesByStateName(String stateName);
}