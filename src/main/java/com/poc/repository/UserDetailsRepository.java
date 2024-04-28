package com.poc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poc.entity.UserDetails;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
	
	UserDetails findByMobileNumber(String mobileNumber);
	
	@Query(value = "SELECT ud.mobileNumber AS MobileNumber, ve.newspaper_name AS NewsPapers, mbj.DELIVERY_TIME AS BatchTime " +
            "FROM USER_DETAILS ud " +
            "JOIN UX_USER_SUBSCRIPTION uxs ON ud.UserID = uxs.user_id " +
            "JOIN VENDOR_DYNAMIC_GENERIC vdg ON uxs.newspaper_id = vdg.dynamic_id " +
            "JOIN VENDOR_TELANGANA_EENADU ve ON vdg.telangana_eenadu_id = ve.newspaper_id " +
            "JOIN MASTER_BATCH_JOBS mbj ON uxs.batch_id = mbj.BATCH_ID " +
            "WHERE ud.mobileNumber = :mobileNumber", nativeQuery = true)
List<Object[]> findSubscriptionDetailsByMobileNumber(String mobileNumber);
}
