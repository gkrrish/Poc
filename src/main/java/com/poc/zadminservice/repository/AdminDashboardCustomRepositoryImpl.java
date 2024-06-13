package com.poc.zadminservice.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class AdminDashboardCustomRepositoryImpl implements AdminDashboardCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Long findTotalDistinctReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(DISTINCT us.user_id) " +
            "FROM USER_SUBSCRIPTION US " +
            "WHERE US.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? ((BigDecimal) result).longValue() : 0L;
    }
    

    @Override
    public Long findTotalReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(*) " +
            "FROM USER_SUBSCRIPTION US " +
            "WHERE US.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? ((BigDecimal) result).longValue() : 0L;
    }

    @Override
    public Long findTodayPositiveDeltaReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM USER_SUBSCRIPTION " +
                "WHERE TRUNC(subscription_start_date) = TRUNC(CURRENT_DATE) " +
                "AND newspaper_master_id = :newspaperMasterId");
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? ((BigDecimal) result).longValue() : 0L;
    }

    @Override
    public Long findTodayNegativeDeltaReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM USER_SUBSCRIPTION " +
                "WHERE TRUNC(subscription_end_date) = TRUNC(CURRENT_DATE) " +
                "AND newspaper_master_id = :newspaperMasterId");
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? ((BigDecimal) result).longValue() : 0L;
    }

    @Override
    public List<Object[]> findStatewiseDeltaReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery("SELECT MS.state_name AS state, " +
                "SUM(CASE WHEN TRUNC(US.subscription_start_date) = TRUNC(CURRENT_DATE) THEN 1 ELSE 0 END) AS totalPositiveDeltaInState, " +
                "SUM(CASE WHEN TRUNC(US.subscription_end_date) = TRUNC(CURRENT_DATE) THEN 1 ELSE 0 END) AS totalNegativeDeltaInState " +
                "FROM USER_SUBSCRIPTION US " +
                "JOIN USER_DETAILS UD ON US.user_id = UD.UserID " +
                "JOIN MASTER_STATEWISE_LOCATIONS MSL ON UD.Location = MSL.location_name " +
                "JOIN MASTER_STATES MS ON MSL.state_id = MS.state_id " +
                "WHERE US.newspaper_master_id = :newspaperMasterId " +
                "GROUP BY MS.state_name");
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return query.getResultList();
    }

    @Override
    public List<Object[]> findDistrictwiseDeltaReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery("SELECT MD.district_name AS district, " +
                "SUM(CASE WHEN TRUNC(US.subscription_start_date) = TRUNC(CURRENT_DATE) THEN 1 ELSE 0 END) AS totalPositiveDeltaInDistrict, " +
                "SUM(CASE WHEN TRUNC(US.subscription_end_date) = TRUNC(CURRENT_DATE) THEN 1 ELSE 0 END) AS totalNegativeDeltaInDistrict " +
                "FROM USER_SUBSCRIPTION US " +
                "JOIN USER_DETAILS UD ON US.user_id = UD.UserID " +
                "JOIN MASTER_STATEWISE_LOCATIONS MSL ON UD.Location = MSL.location_name " +
                "JOIN MASTER_DISTRICTS MD ON MSL.district_id = MD.district_id " +
                "WHERE US.newspaper_master_id = :newspaperMasterId " +
                "GROUP BY MD.district_name");
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return query.getResultList();
    }

    @Override
    public List<Object[]> findMandalwiseDeltaReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery("SELECT MM.mandal_name AS mandal, " +
                "SUM(CASE WHEN TRUNC(US.subscription_start_date) = TRUNC(CURRENT_DATE) THEN 1 ELSE 0 END) AS totalPositiveDeltaInMandal, " +
                "SUM(CASE WHEN TRUNC(US.subscription_end_date) = TRUNC(CURRENT_DATE) THEN 1 ELSE 0 END) AS totalNegativeDeltaInMandal " +
                "FROM USER_SUBSCRIPTION US " +
                "JOIN USER_DETAILS UD ON US.user_id = UD.UserID " +
                "JOIN MASTER_STATEWISE_LOCATIONS MSL ON UD.Location = MSL.location_name " +
                "JOIN MASTER_MANDALS MM ON MSL.mandal_id = MM.mandal_id " +
                "WHERE US.newspaper_master_id = :newspaperMasterId " +
                "GROUP BY MM.mandal_name");
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return query.getResultList();
    }

    @Override
    public String findMostScheduledBatchTimeNewspaper(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT DELIVERY_TIME " +
            "FROM (" +
            "   SELECT DELIVERY_TIME, ROW_NUMBER() OVER (ORDER BY COUNT(*) DESC) AS rn " +
            "   FROM USER_SUBSCRIPTION US " +
            "   JOIN MASTER_BATCH_JOBS MBJ ON US.batch_id = MBJ.BATCH_ID " +
            "   JOIN MASTER_NEWSPAPER MN ON US.newspaper_master_id = MN.newspaper_master_id " +
            "   WHERE MN.newspaper_master_id = :newspaperMasterId " +
            "   GROUP BY DELIVERY_TIME " +
            ") " +
            "WHERE rn = 1"
        );
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? result.toString() : null;
    }


}
