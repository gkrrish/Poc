package com.poc.auser.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.auser.master.entity.Country;


@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {
	
	Country findByCountryName(String countryName);
}