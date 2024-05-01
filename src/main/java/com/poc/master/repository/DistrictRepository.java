package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.District;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
}