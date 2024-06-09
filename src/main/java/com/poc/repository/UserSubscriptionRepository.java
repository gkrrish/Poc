package com.poc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.entity.UserSubscription;
import com.poc.entity.UserSubscriptionId;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {

	Optional<UserSubscription> findById_User_UseridAndId_Vendor_Id_NewspaperId(Long userid, Long newspaperId);
}