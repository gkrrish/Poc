package com.poc.z.newspaper.adminservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.entity.UserNewspaperSubscription;

@Repository
public interface AdminDashboardRepository extends JpaRepository<UserNewspaperSubscription, Long> {
}