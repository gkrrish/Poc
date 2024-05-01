package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.StatewiseLocation;

@Repository
public interface StatewiseLocationRepository extends JpaRepository<StatewiseLocation, Long> {
}
