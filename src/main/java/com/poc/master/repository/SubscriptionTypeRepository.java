package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.SubscriptionType;

@Repository
public interface SubscriptionTypeRepository extends JpaRepository<SubscriptionType, Long> {
}