package com.poc.z.newspaper.adminservice.repository;

import java.util.Date;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

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
    
    
    /**
     * NOTE : whenever we updating the UserSubscription with location change for the user, then we are going to use this, but instead of this delete the record 
     * of the user and create the specific record also looks fine. and VENDOR and USE_SUBSCRIPTION has composite pk and pk makes circular dependency mind it. 
     * 
     * Your USER_SUBSCRIPTION table has a composite foreign key that references VENDORS, which itself references MASTER_STATEWISE_LOCATIONS. This might be a 
     * bit complex but not necessarily circular. However, ensuring proper insertion order is crucial to avoid dependency issues.
     */
    @Transactional
    public void updateLocationForUserSubscription(int userId, int vendeorNewspaperId, int newLocationId) {
        // Disable the foreign key constraint
        Query disableConstraintQuery = entityManager.createNativeQuery(
            "ALTER TABLE USER_SUBSCRIPTION DISABLE CONSTRAINT FK_USER_SUBSCRIPTION_VENDORS"
        );
        disableConstraintQuery.executeUpdate();

        // Update the location_id in the VENDORS table
        Query updateVendorsQuery = entityManager.createNativeQuery(
            "UPDATE VENDORS SET location_id = :newLocationId WHERE newspaper_id = :newspaperId AND location_id != :newLocationId"
        );
        updateVendorsQuery.setParameter("newLocationId", newLocationId);
        updateVendorsQuery.setParameter("newspaperId", vendeorNewspaperId);
        updateVendorsQuery.executeUpdate();

        // Update the location_id in the USER_SUBSCRIPTION table
        Query updateUserSubscriptionQuery = entityManager.createNativeQuery(
            "UPDATE USER_SUBSCRIPTION SET location_id = :newLocationId WHERE user_id = :userId AND newspaper_id = :newspaperId"
        );
        updateUserSubscriptionQuery.setParameter("newLocationId", newLocationId);
        updateUserSubscriptionQuery.setParameter("userId", userId);
        updateUserSubscriptionQuery.setParameter("newspaperId", vendeorNewspaperId);
        updateUserSubscriptionQuery.executeUpdate();

        // Re-enable the foreign key constraint
        Query enableConstraintQuery = entityManager.createNativeQuery(
            "ALTER TABLE USER_SUBSCRIPTION ENABLE CONSTRAINT FK_USER_SUBSCRIPTION_VENDORS"
        );
        enableConstraintQuery.executeUpdate();
    }
}
