package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
}