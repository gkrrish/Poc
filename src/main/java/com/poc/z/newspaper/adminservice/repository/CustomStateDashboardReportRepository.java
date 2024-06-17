package com.poc.z.newspaper.adminservice.repository;

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
            "SELECT COUNT(DISTINCT UD.UserID) " +
            "FROM USER_SUBSCRIPTION US " +
            "JOIN USER_DETAILS UD ON US.user_id = UD.UserID " +
            "JOIN MASTER_STATEWISE_LOCATIONS MSL ON UD.Location = MSL.location_name " +
            "JOIN MASTER_STATES MS ON MSL.state_id = MS.state_id " +
            "WHERE MS.state_name = :stateName AND US.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findStatewiseReaders(String stateName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(US.user_id) " +
            "FROM USER_SUBSCRIPTION US " +
            "JOIN USER_DETAILS UD ON US.user_id = UD.UserID " +
            "JOIN MASTER_STATEWISE_LOCATIONS MSL ON UD.Location = MSL.location_name " +
            "JOIN MASTER_STATES MS ON MSL.state_id = MS.state_id " +
            "WHERE MS.state_name = :stateName AND US.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findTotalPositiveDeltaInState(String stateName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(*) " +
            "FROM USER_SUBSCRIPTION US " +
            "JOIN USER_DETAILS UD ON US.user_id = UD.UserID " +
            "JOIN MASTER_STATEWISE_LOCATIONS MSL ON UD.Location = MSL.location_name " +
            "JOIN MASTER_STATES MS ON MSL.state_id = MS.state_id " +
            "WHERE MS.state_name = :stateName AND US.newspaper_master_id = :newspaperMasterId " +
            "AND TRUNC(US.subscription_start_date) = TRUNC(CURRENT_DATE)"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findTotalNegativeDeltaInState(String stateName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(*) " +
            "FROM USER_SUBSCRIPTION US " +
            "JOIN USER_DETAILS UD ON US.user_id = UD.UserID " +
            "JOIN MASTER_STATEWISE_LOCATIONS MSL ON UD.Location = MSL.location_name " +
            "JOIN MASTER_STATES MS ON MSL.state_id = MS.state_id " +
            "WHERE MS.state_name = :stateName AND US.newspaper_master_id = :newspaperMasterId " +
            "AND TRUNC(US.subscription_end_date) = TRUNC(CURRENT_DATE)"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }
}
