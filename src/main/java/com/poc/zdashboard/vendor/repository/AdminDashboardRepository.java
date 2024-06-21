package com.poc.zdashboard.vendor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.auser.main.entity.UserNewspaperSubscription;

@Repository
public interface AdminDashboardRepository extends JpaRepository<UserNewspaperSubscription, Long> {
}
