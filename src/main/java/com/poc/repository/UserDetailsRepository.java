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
	
	@Query(value = "SELECT ud.UserID, " +
            "ud.mobileNumber, " +
            "mn.newspaper_name AS newspaper_name, " +
            "mnl.language_name AS newspaper_language, " +
            "ms.state_name AS state, " +
            "md.district_name AS district, " +
            "mm.mandal_name AS mandal, " +
            "mbj.DELIVERY_TIME AS scheduled_time, " +
            "st.subscriptionduration AS subscription_duration, " +
            "st.subscriptionfee AS monthly_subscription_charges " +
            "FROM USER_DETAILS ud " +
            "INNER JOIN USER_SUBSCRIPTION us ON ud.UserID = us.user_id " +
            "INNER JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "INNER JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
            "INNER JOIN MASTER_NEWS_LANGUAGES mnl ON v.newspaper_language = mnl.language_id " +
            "INNER JOIN MASTER_STATEWISE_LOCATIONS mswl ON v.location_id = mswl.location_id " +
            "INNER JOIN MASTER_STATES ms ON mswl.state_id = ms.state_id " +
            "INNER JOIN MASTER_DISTRICTS md ON mswl.district_id = md.district_id " +
            "INNER JOIN MASTER_MANDALS mm ON mswl.mandal_id = mm.mandal_id " +
            "INNER JOIN MASTER_BATCH_JOBS mbj ON us.batch_id = mbj.BATCH_ID " +
            "INNER JOIN SUBSCRIPTION_TYPE st ON v.subscription_type_id = st.subscriptiontypeid " +
            "WHERE ud.mobileNumber = :mobileNumber",
            nativeQuery = true)
    List<Object[]> getUserDetailsByMobileNumber(@Param("mobileNumber") String mobileNumber);

}
