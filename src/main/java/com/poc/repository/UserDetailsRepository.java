package com.poc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poc.entity.UserDetails;


@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
	
	UserDetails findByMobileNumber(String mobileNumber);
	
	@Query(value = "SELECT ud.mobilenumber AS mobileNumber, " +
            "v.newspaper_name AS newspaperName, " +
            "mil.language_name AS language, " +
            "ms.state_name AS state, " +
            "md.district_name AS district, " +
            "mm.mandal_name AS mandal, " +
            "mbj.delivery_time AS batchTime, " +
            "st.subscriptionfee AS subscriptionCharges " +
            "FROM USER_DETAILS ud " +
            "JOIN USER_SUBSCRIPTION us ON ud.UserID = us.user_id " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_NEWS_LANGUAGES mil ON v.newspaper_language = mil.language_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON us.location_id = msl.location_id " +
            "JOIN MASTER_MANDALS mm ON msl.mandal_id = mm.mandal_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
            "JOIN MASTER_BATCH_JOBS mbj ON us.batch_id = mbj.BATCH_ID " +
            "JOIN SUBSCRIPTION_TYPE st ON v.subscription_type_id = st.subscriptiontypeid " +
            "WHERE ud.mobilenumber = :mobileNumber " +
            "AND v.vendor_id IN ( " +
            "    SELECT vendorid " +
            "    FROM VENDOR_DETAILS " +
            "    WHERE vendorstatus = 'active' " +
            ")",
            nativeQuery = true)
    List<Object[]> getUserDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);

}
