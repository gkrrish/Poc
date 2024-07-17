package com.poc.master.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.poc.response.AvailableNewspapersByMandalwise;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class CustomMandalDashboardReportRepository {
    
    @PersistenceContext
    private EntityManager entityManager;

    public Long findMandalwiseDistinctUsers(String mandalName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(DISTINCT us.user_id) AS distinct_user_subscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_MANDALS mm ON msl.mandal_id = mm.mandal_id " +
            "WHERE mm.mandal_name = :mandalName " +
            "AND v.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("mandalName", mandalName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findMandalwiseReaders(String mandalName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(us.user_id) AS user_subscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_MANDALS mm ON msl.mandal_id = mm.mandal_id " +
            "WHERE mm.mandal_name = :mandalName " +
            "AND v.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("mandalName", mandalName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findTotalPositiveDeltaInMandal(String mandalName, int newspaperMasterId, Date subscriptionStartDate, Date givenDate) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(us.user_id) AS user_subscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_MANDALS mm ON msl.mandal_id = mm.mandal_id " +
            "WHERE mm.mandal_name = :mandalName " +
            "AND v.newspaper_master_id = :newspaperMasterId " +
            "AND us.subscription_start_date >= :subscriptionStartDate " +
            "AND us.subscription_start_date <= :givenDate"
        );
        query.setParameter("mandalName", mandalName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        query.setParameter("subscriptionStartDate", subscriptionStartDate);
        query.setParameter("givenDate", givenDate);
        return ((Number) query.getSingleResult()).longValue();
    }

    public Long findTotalNegativeDeltaInMandal(String mandalName, int newspaperMasterId, Date subscriptionEndDateStart, Date givenEndDate) {
        Query query = entityManager.createNativeQuery(
            "SELECT COUNT(us.user_id) AS user_unsubscribers " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_MANDALS mm ON msl.mandal_id = mm.mandal_id " +
            "WHERE mm.mandal_name = :mandalName " +
            "AND v.newspaper_master_id = :newspaperMasterId " +
            "AND us.subscription_end_date >= :subscriptionEndDateStart " +
            "AND us.subscription_end_date <= :givenEndDate"
        );
        query.setParameter("mandalName", mandalName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        query.setParameter("subscriptionEndDateStart", subscriptionEndDateStart);
        query.setParameter("givenEndDate", givenEndDate);
        return ((Number) query.getSingleResult()).longValue();
    }

    public String findMostScheduledBatchTimeNewspaperInMandal(String mandalName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT DELIVERY_TIME " +
            "FROM (" +
            "   SELECT MBJ.DELIVERY_TIME, ROW_NUMBER() OVER (ORDER BY COUNT(*) DESC) AS rn " +
            "   FROM USER_SUBSCRIPTION US " +
            "   JOIN MASTER_BATCH_JOBS MBJ ON US.batch_id = MBJ.BATCH_ID " +
            "   JOIN VENDORS V ON US.newspaper_id = V.newspaper_id " +
            "   JOIN MASTER_STATEWISE_LOCATIONS MSL ON V.location_id = MSL.location_id " +
            "   JOIN MASTER_MANDALS MM ON MSL.mandal_id = MM.mandal_id " +
            "   WHERE MM.mandal_name = :mandalName " +
            "   AND V.newspaper_master_id = :newspaperMasterId " +
            "   GROUP BY MBJ.DELIVERY_TIME " +
            ") " +
            "WHERE rn = 1"
        );
        query.setParameter("mandalName", mandalName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        Object result = query.getSingleResult();
        return result != null ? result.toString() : null;
    }
    
    public List<String> findMandalsByDistrictAndNewspaperMasterId(String districtName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT DISTINCT mm.mandal_name " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_MANDALS mm ON msl.mandal_id = mm.mandal_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "WHERE v.newspaper_master_id = :newspaperMasterId " +
            "AND md.district_name = :districtName"
        );
        query.setParameter("districtName", districtName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return query.getResultList();
    }
    
    public List<String> getDistrictsByStateAndNewspaperMasterId(String stateName, int newspaperMasterId) {
        Query query = entityManager.createNativeQuery(
            "SELECT DISTINCT md.district_name " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
            "WHERE ms.state_name = :stateName " +
            "AND v.newspaper_master_id = :newspaperMasterId"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("newspaperMasterId", newspaperMasterId);
        return query.getResultList();
    }
    
    public AvailableNewspapersByMandalwise getAvailableNewspapersByMandalwise(String stateName, String districtName) {
        Query query = entityManager.createNativeQuery(
                "SELECT mn.newspaper_name, mm.mandal_name " +
                        "FROM VENDORS v " +
                        "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                        "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
                        "JOIN MASTER_MANDALS mm ON msl.mandal_id = mm.mandal_id " +
                        "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
                        "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
                        "WHERE ms.state_name = :stateName " +
                        "AND md.district_name = :districtName"
        );
        query.setParameter("stateName", stateName);
        query.setParameter("districtName", districtName);

        List<Object[]> result = query.getResultList();

        Map<String, List<String>> availableMandals = result.stream()
                .collect(Collectors.groupingBy(
                        row -> (String) row[0], // newspaper_name
                        Collectors.mapping(row -> (String) row[1], Collectors.toList()) // mandal_name
                ));

        AvailableNewspapersByMandalwise response = new AvailableNewspapersByMandalwise();
        response.setStateName(stateName);
        response.setAvailableMandals(availableMandals);

        return response;
    }
}
