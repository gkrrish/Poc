package com.poc.y.app.ownerdashboard.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.poc.y.app.ownerdashboard.response.TrendingNewspaperResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
@Transactional
@SuppressWarnings("unchecked")
public class TrendingNewspaperRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<TrendingNewspaperResponse> getTrendingNewspaper(Date startDate, Date endDate) {
        Query query = entityManager.createNativeQuery(
            "SELECT newspaper_name, subscriber_count FROM (" +
                    "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
                    "FROM USER_SUBSCRIPTION us " +
                    "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                    "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                    "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
                    "GROUP BY mn.newspaper_name " +
                    "ORDER BY subscriber_count DESC" +
                    ") WHERE ROWNUM = 1"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<Object[]> resultList = query.getResultList();
        return resultList.stream().map(row -> 
            new TrendingNewspaperResponse(
                (String) row[0], 
                null, 
                null, 
                null, 
                ((BigDecimal) row[1]).longValue()
            )
        ).collect(Collectors.toList());
    }

    public List<TrendingNewspaperResponse> getTrendingNewspaperByLanguage(Date startDate, Date endDate, String language) {
        Query query = entityManager.createNativeQuery(
                "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
                        "FROM USER_SUBSCRIPTION us " +
                        "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                        "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                        "JOIN MASTER_NEWS_LANGUAGES mnl ON v.newspaper_language = mnl.language_id " +
                        "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
                        "AND mnl.language_name = :language " +
                        "GROUP BY mn.newspaper_name " +
                        "ORDER BY subscriber_count DESC"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("language", language);

        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
                .map(row -> new TrendingNewspaperResponse(
                        (String) row[0], 
                        language, 
                        null, 
                        null, 
                        ((BigDecimal) row[1]).longValue()
                ))
                .collect(Collectors.toList());
    }


    public List<TrendingNewspaperResponse> getTopTenTrendingNewspapersByLanguage(Date startDate, Date endDate, String language) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM ( " +
                "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
                "FROM USER_SUBSCRIPTION us " +
                "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                "JOIN MASTER_NEWS_LANGUAGES mnl ON v.newspaper_language = mnl.language_id " +
                "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
                "AND mnl.language_name = :language " +
                "GROUP BY mn.newspaper_name " +
                "ORDER BY subscriber_count DESC) " +
                "WHERE ROWNUM <= 10"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("language", language);

        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
                .map(row -> new TrendingNewspaperResponse(
                        (String) row[0], 
                        language, 
                        null, 
                        null, 
                        ((BigDecimal) row[1]).longValue()
                ))
                .collect(Collectors.toList());
    }

    
    
    public List<TrendingNewspaperResponse> getTrendingNewspaperByState(Date startDate, Date endDate, String stateName) {
        Query query = entityManager.createNativeQuery(
            "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
            "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
            "AND ms.state_name = :stateName " +
            "GROUP BY mn.newspaper_name " +
            "ORDER BY subscriber_count DESC"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("stateName", stateName);

        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
                .map(row -> new TrendingNewspaperResponse(
                        (String) row[0], 
                        null, 
                        stateName, 
                        null, 
                        ((BigDecimal) row[1]).longValue()
                ))
                .collect(Collectors.toList());
    }

    

    public List<TrendingNewspaperResponse> getTrendingNewspaperByDistrict(Date startDate, Date endDate, Long districtId) {
        Query query = entityManager.createNativeQuery(
            "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
            "FROM USER_SUBSCRIPTION us " +
            "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
            "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
            "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
            "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
            "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
            "AND md.district_id = :districtId " +
            "GROUP BY mn.newspaper_name " +
            "ORDER BY subscriber_count DESC"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("districtId", districtId);

        List<Object[]> resultList = query.getResultList();
        return resultList.stream()
                .map(row -> new TrendingNewspaperResponse(
                        (String) row[0], 
                        null, 
                        null, 
                        null, 
                        ((BigDecimal) row[1]).longValue()
                ))
                .collect(Collectors.toList());
    }


    public List<TrendingNewspaperResponse> getTrendingNewspaperByStateByLanguage(Date startDate, Date endDate, String stateName, String language) {
        Query query = entityManager.createNativeQuery(
                "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
                        "FROM USER_SUBSCRIPTION us " +
                        "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                        "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                        "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
                        "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
                        "JOIN MASTER_NEWS_LANGUAGES mnl ON v.newspaper_language = mnl.language_id " +
                        "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
                        "AND ms.state_name = :stateName " +
                        "AND mnl.language_name = :language " +
                        "GROUP BY mn.newspaper_name " +
                        "ORDER BY subscriber_count DESC"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("stateName", stateName);
        query.setParameter("language", language);

        List<Object[]> result = query.getResultList();
        return mapToTrendingNewspaperResponse(result, language, stateName);
    }

    private List<TrendingNewspaperResponse> mapToTrendingNewspaperResponse(List<Object[]> result, String language, String stateName) {
        return result.stream()
                     .map(row -> new TrendingNewspaperResponse((String) row[0], language, stateName, null, ((Number) row[1]).longValue()))
                     .collect(Collectors.toList());
    }
    
   
    public List<TrendingNewspaperResponse> getTrendingNewspaperByDistrictByLanguage(Date startDate, Date endDate, Long districtId, String language) {
        Query query = entityManager.createNativeQuery(
                "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
                        "FROM USER_SUBSCRIPTION us " +
                        "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                        "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                        "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
                        "JOIN MASTER_DISTRICTS md ON msl.district_id = md.district_id " +
                        "JOIN MASTER_NEWS_LANGUAGES mnl ON v.newspaper_language = mnl.language_id " +
                        "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
                        "AND md.district_id = :districtId " +
                        "AND mnl.language_name = :language " +
                        "GROUP BY mn.newspaper_name " +
                        "ORDER BY subscriber_count DESC"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("districtId", districtId);
        query.setParameter("language", language);

        List<Object[]> result = query.getResultList();
        return mapToTrendingNewspaperResponse(result, language);
    }


    private List<TrendingNewspaperResponse> mapToTrendingNewspaperResponse(List<Object[]> result, String language) {
        return result.stream()
                     .map(row -> new TrendingNewspaperResponse((String) row[0], language, null, null, ((Number) row[1]).longValue()))
                     .collect(Collectors.toList());
    }


    public List<TrendingNewspaperResponse> getTopTenNewspapersByState(Date startDate, Date endDate, String stateName) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM ( " +
                "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
                "FROM USER_SUBSCRIPTION us " +
                "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
                "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
                "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
                "AND ms.state_name = :stateName " +
                "GROUP BY mn.newspaper_name " +
                "ORDER BY subscriber_count DESC " +
                ") WHERE ROWNUM <= 10"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("stateName", stateName);

        List<Object[]> result = query.getResultList();
        return result.stream()
                     .map(row -> new TrendingNewspaperResponse(
                             (String) row[0], 
                             null, 
                             stateName, 
                             null, 
                             ((BigDecimal) row[1]).longValue()
                     ))
                     .collect(Collectors.toList());
    }
 
    
    public List<TrendingNewspaperResponse> getTopTenNewspapersByStateByLanguage(Date startDate, Date endDate, String stateName, String language) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM ( " +
                "SELECT mn.newspaper_name, COUNT(us.user_id) AS subscriber_count " +
                "FROM USER_SUBSCRIPTION us " +
                "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                "JOIN MASTER_STATEWISE_LOCATIONS msl ON v.location_id = msl.location_id " +
                "JOIN MASTER_STATES ms ON msl.state_id = ms.state_id " +
                "JOIN MASTER_NEWS_LANGUAGES mnl ON v.newspaper_language = mnl.language_id " +
                "WHERE us.subscription_start_date BETWEEN :startDate AND :endDate " +
                "AND ms.state_name = :stateName " +
                "AND mnl.language_name = :language " +
                "GROUP BY mn.newspaper_name " +
                "ORDER BY subscriber_count DESC " +
                ") WHERE ROWNUM <= 10"
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("stateName", stateName);
        query.setParameter("language", language);

        List<Object[]> result = query.getResultList();
        return result.stream()
                     .map(row -> new TrendingNewspaperResponse(
                             (String) row[0], 
                             language, 
                             stateName, 
                             null, 
                             ((BigDecimal) row[1]).longValue()
                     ))
                     .collect(Collectors.toList());
    }
    
   
    public List<TrendingNewspaperResponse> getTopDeclinedNewspaper(Date startDate, Date endDate) {
        Query query = entityManager.createNativeQuery(
                "SELECT mn.newspaper_name, COUNT(us.user_id) AS unsubscriber_count " +
                        "FROM USER_SUBSCRIPTION us " +
                        "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                        "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                        "WHERE us.subscription_end_date BETWEEN :startDate AND :endDate " +
                        "GROUP BY mn.newspaper_name " +
                        "ORDER BY unsubscriber_count DESC "
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<Object[]> result = query.getResultList();
        return mapToTrendingNewspaperResponse(result);
    }
    
    private List<TrendingNewspaperResponse> mapToTrendingNewspaperResponse(List<Object[]> result) {
        return result.stream()
                     .map(row -> new TrendingNewspaperResponse((String) row[0], null, null, null, ((Number) row[1]).longValue()))
                     .collect(Collectors.toList());
    }
    

    public List<TrendingNewspaperResponse> getTopDeclinedNewspaperByLanguage(Date startDate, Date endDate, String language) {
        Query query = entityManager.createNativeQuery(
                "SELECT mn.newspaper_name, COUNT(us.user_id) AS unsubscriber_count " +
                        "FROM USER_SUBSCRIPTION us " +
                        "JOIN VENDORS v ON us.newspaper_id = v.newspaper_id " +
                        "JOIN MASTER_NEWSPAPER mn ON v.newspaper_master_id = mn.newspaper_master_id " +
                        "JOIN MASTER_NEWS_LANGUAGES mnl ON v.newspaper_language = mnl.language_id " +
                        "WHERE us.subscription_end_date BETWEEN :startDate AND :endDate " +
                        "AND mnl.language_name = :language " +
                        "GROUP BY mn.newspaper_name " +
                        "ORDER BY unsubscriber_count DESC "
        );
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("language", language);

        List<Object[]> result = query.getResultList();
        return mapToTrendingNewspaperResponse(result);
    }

}
