package com.poc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.entity.UserSubscription;
import com.poc.entity.UserSubscriptionId;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, UserSubscriptionId> {

	//NOTE: In USER-SUBCRIPTION --UserSubscriptionId-- UserDetails --userid-- not userId (not USER_ID, its only USERID)
	@Query("SELECT us FROM UserSubscription us WHERE us.id.user.userid = :userId AND us.id.vendor.id.newspaperId = :newspaperId")
	Optional<UserSubscription> findByUserIdAndNewspaperId(@Param("userId") Long userId, @Param("newspaperId") Long newspaperId);
	
}