package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {
}
