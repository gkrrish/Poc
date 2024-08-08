package com.poc.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.main.entity.DailyBanner;

@Repository
public interface DailyBannerRepository extends JpaRepository<DailyBanner, Integer> {
	
	@Query("SELECT d FROM DailyBanner d WHERE d.location.locationId = :locationId AND d.newspaper.id = :newspaperId")
    Optional<DailyBanner> findByLocationIdAndNewspaperId(@Param("locationId") Long locationId, @Param("newspaperId") Long newspaperId);
}