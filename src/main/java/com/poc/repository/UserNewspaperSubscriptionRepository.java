package com.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.entity.UserNewspaperSubscription;

@Repository
public interface UserNewspaperSubscriptionRepository extends JpaRepository<UserNewspaperSubscription, Long> {

}
