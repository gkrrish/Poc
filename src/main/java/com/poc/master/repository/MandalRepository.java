package com.poc.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.Mandal;

@Repository
public interface MandalRepository extends JpaRepository<Mandal, Long> {
	
	@Query("SELECT m.mandalName FROM Mandal m WHERE m.district.districtName = :districtName")
    List<String> findAllMandalNamesByDistrictName(@Param("districtName") String districtName);
}
