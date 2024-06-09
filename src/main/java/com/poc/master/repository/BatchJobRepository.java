package com.poc.master.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.BatchJob;

@Repository
public interface BatchJobRepository extends JpaRepository<BatchJob, Long> {

	@Query("SELECT bj.deliveryTime FROM BatchJob bj")
	List<String> findAllDeliveryTimes();

	Optional<BatchJob> findByDeliveryTime(String deliveryTime);
}