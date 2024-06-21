package com.poc.zdashboard.vendor.repository;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CustomDistrictDashboardReportRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Long findDistrictwiseDistinctUsers(String districtName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(DISTINCT us.user_id) AS distinct_user_subscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "WHERE md.district_name = :districtName " +
            "AND v.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("districtName", districtName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findDistrictwiseReaders(String districtName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(us.user_id) AS user_subscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "WHERE md.district_name = :districtName " +
            "AND v.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("districtName", districtName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findTotalPositiveDeltaInDistrict(String districtName, int newspaperMasterId, Date subscriptionStartDate, Date givenDate) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(us.user_id) AS user_subscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "WHERE md.district_name = :districtName " +
            "AND v.newspaper_master_id = :newspaperMasterId " +
            "AND us.subscription_start_date >= :subscriptionStartDate " +
            "AND us.subscription_start_date <= :givenDate"
        );
        query.setParameter("districtName", districtName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        query.setParameter("subscriptionStartDate", subscriptionStartDate);
        query.setParameter("givenDate", givenDate);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findTotalNegativeDeltaInDistrict(String districtName, int newspaperMasterId, Date subscriptionEndDateStart, Date givenEndDate) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(us.user_id) AS user_unsubscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "WHERE md.district_name = :districtName " +
            "AND v.newspaper_master_id = :newspaperMasterId " +
            "AND us.subscription_end_date >= :subscriptionEndDateStart " +
            "AND us.subscription_end_date <= :givenEndDate"
        );
        query.setParameter("districtName", districtName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        query.setParameter("subscriptionEndDateStart", subscriptionEndDateStart);
        query.setParameter("givenEndDate", givenEndDate);
        return ((Number) query.getSingleResult()).longValue();
    }

    public String findMostScheduledBatchTimeNewspaperInDistrict(String districtName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT DELIVERY_TIME " +
            "FROM (" +
            "   SELECT MBJ.DELIVERY_TIME, ROW_NUMBER() OVER (ORDER BY COUNT(*) DESC) AS rn " +
            "   FROM USER_SUBSCRIPTION US " +
            "   JOIN MASTER_BATCH_JOBS MBJ ON US.batch_id = MBJ.BATCH_ID " +
            "   JOIN VENDORS V ON US.newspaper_id = V.newspaper_id " +
            "   JOIN MASTER_STATEWISE_LOCATIONS MSL ON V.location_id = MSL.location_id " +
            "   JOIN MASTER_DISTRICTS MD ON MSL.district_id = MD.district_id " +
            "   WHERE MD.district_name = :districtName " +
            "   AND V.newspaper_master_id = :newspaperMasterId " +
            "   GROUP BY MBJ.DELIVERY_TIME " +
            ") " +
            "WHERE rn = 1"
        );
        query.setParameter("districtName", districtName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? result.toString() : null;
    }
    
    
    
    public List<String> findDistrictsByStateAndNewspaperMasterId(String stateName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT DISTINCT md.district_name " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
            "WHERE v.newspaper_master_id = :newspaperMasterId " +
            "AND ms.state_name = :stateName"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return query.getResultList();
    }
}
