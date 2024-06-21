package com.poc.zdashboard.vendor.repository;

import java.math.BigDecimal;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class AdminDashboardCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;
    
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

    public Long findTodayPositiveDeltaReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM USER_SUBSCRIPTION " +
                "WHERE TRUNC(subscription_start_date) = TRUNC(CURRENT_DATE) " +
                "AND newspaper_master_id = :newspaperMasterId");
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? ((BigDecimal) result).longValue() : 0L;
    }

    public Long findTodayNegativeDeltaReaders(int newspaperMasterId) {
        Query query = entityManager.createNativeQuery("SELECT COUNT(*) FROM USER_SUBSCRIPTION " +
                "WHERE TRUNC(subscription_end_date) = TRUNC(CURRENT_DATE) " +
                "AND newspaper_master_id = :newspaperMasterId");
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? ((BigDecimal) result).longValue() : 0L;
    }

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
