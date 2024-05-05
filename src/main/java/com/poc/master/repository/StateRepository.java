package com.poc.master.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
	List<State> findAllByCountryId(Integer countryId);
}
