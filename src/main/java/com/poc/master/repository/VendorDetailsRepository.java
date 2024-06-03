package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.VendorDetails;

@Repository
public interface VendorDetailsRepository extends JpaRepository<VendorDetails, Integer> {
}