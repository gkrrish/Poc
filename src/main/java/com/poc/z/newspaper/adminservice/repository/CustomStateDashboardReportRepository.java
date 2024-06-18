package com.poc.z.newspaper.adminservice.repository;

import java.util.Date;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CustomStateDashboardReportRepository {
	
	@PersistenceContext
    private EntityManager entityManager;

	public Long findStatewiseDistinctUsers(String stateName, int newspaperMasterId) {
	    Query query = entityManager.createNativeQuery(
	        "SELECT COUNT(DISTINCT us.user_id) AS distinct_user_subscribers " +
	        "FROM USER_SUBSCRIPTION us " +
	        "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
	        "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
	        "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
	        "WHERE ms.state_name = :stateName " +
	        "AND v.newspaper_master_id = :newspaperMasterId"
	    );
	    query.setParameter("stateName", stateName);
	    query.setParameter("newspaperMasterId", newspaperMasterId);
	    return ((Number) query.getSingleResult()).longValue();
	}

    public Long findStatewiseReaders(String stateName, int newspaperMasterId) {
    	Query query = entityManager.createNativeQuery(
    	        "SELECT COUNT(us.user_id) AS user_subscribers " +
    	        "FROM USER_SUBSCRIPTION us " +
    	        "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
    	        "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
    	        "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
    	        "WHERE ms.state_name = :stateName " +
    	        "AND v.newspaper_master_id = :newspaperMasterId"
    	);
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findTotalPositiveDeltaInState(String stateName, int newspaperMasterId, Date subscriptionStartDate, Date givenDate) {
        Query query = entityManager.createNativeQuery(
                "SELECT COUNT(us.user_id) AS user_subscribers " +
                "FROM USER_SUBSCRIPTION us " +
                "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
                "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
                "WHERE ms.state_name = :stateName " +
                "AND v.newspaper_master_id = :newspaperMasterId " +
                "AND us.subscription_start_date >= :subscriptionStartDate " +
                "AND us.subscription_start_date <= :givenDate"
            );
            query.setParameter("stateName", stateName);
            query.setParameter("newspaperMasterId", newspaperMasterId);
            query.setParameter("subscriptionStartDate", subscriptionStartDate);
            query.setParameter("givenDate", givenDate);
            return ((Number) query.getSingleResult()).longValue();
        }

    public Long findTotalNegativeDeltaInState(String stateName, int newspaperMasterId, Date subscriptionEndDateStart, Date givenEndDate) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(us.user_id) AS user_unsubscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
            "WHERE ms.state_name = :stateName " +
            "AND v.newspaper_master_id = :newspaperMasterId " +
            "AND us.subscription_end_date >= :subscriptionEndDateStart " +
            "AND us.subscription_end_date <= :givenEndDate"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        query.setParameter("subscriptionEndDateStart", subscriptionEndDateStart);
        query.setParameter("givenEndDate", givenEndDate);
        return ((Number) query.getSingleResult()).longValue();
    }
    
    public String findMostScheduledBatchTimeNewspaperInStatewise(String stateName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT DELIVERY_TIME " +
            "FROM (" +
            "   SELECT MBJ.DELIVERY_TIME, ROW_NUMBER() OVER (ORDER BY COUNT(*) DESC) AS rn " +
            "   FROM USER_SUBSCRIPTION US " +
            "   JOIN MASTER_BATCH_JOBS MBJ ON US.batch_id = MBJ.BATCH_ID " +
            "   JOIN VENDORS V ON US.newspaper_id = V.newspaper_id " +
            "   JOIN MASTER_STATEWISE_LOCATIONS MSL ON V.location_id = MSL.location_id " +
            "   JOIN MASTER_STATES MS ON MSL.state_id = MS.state_id " +
            "   WHERE MS.state_name = :stateName " +
            "   AND V.newspaper_master_id = :newspaperMasterId " +
            "   GROUP BY MBJ.DELIVERY_TIME " +
            ") " +
            "WHERE rn = 1"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? result.toString() : null;
    }
}
