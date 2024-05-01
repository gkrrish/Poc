package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.BatchJob;

@Repository
public interface BatchJobRepository extends JpaRepository<BatchJob, Long> {
}