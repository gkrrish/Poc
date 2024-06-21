package com.poc.zdashboard.admin.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poc.zdashboard.admin.repository.TrendingNewspaperRepository;
import com.poc.zdashboard.admin.response.TrendingNewspaperResponse;

@Service
@Transactional
public class TrendingNewspaperService {

    @Autowired
    private TrendingNewspaperRepository trendingNewspaperRepository;

    public TrendingNewspaperResponse getTrendingNewspaper(Date startDate, Date endDate) {
        List<TrendingNewspaperResponse> result = trendingNewspaperRepository.getTrendingNewspaper(startDate, endDate);
        return result.isEmpty() ? null : result.get(0);
    }

    public TrendingNewspaperResponse getTrendingNewspaperByLanguage(Date startDate, Date endDate, String language) {
        List<TrendingNewspaperResponse> result = trendingNewspaperRepository.getTrendingNewspaperByLanguage(startDate, endDate, language);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<TrendingNewspaperResponse> getTopTenTrendingNewspapersByLanguage(Date startDate, Date endDate, String language) {
        return trendingNewspaperRepository.getTopTenTrendingNewspapersByLanguage(startDate, endDate, language);
    }

    public TrendingNewspaperResponse getTrendingNewspaperByState(Date startDate, Date endDate, String stateName) {
        List<TrendingNewspaperResponse> result = trendingNewspaperRepository.getTrendingNewspaperByState(startDate, endDate, stateName);
        return result.isEmpty() ? null : result.get(0);
    }

    public TrendingNewspaperResponse getTrendingNewspaperByDistrict(Date startDate, Date endDate, Long districtId) {
        List<TrendingNewspaperResponse> result = trendingNewspaperRepository.getTrendingNewspaperByDistrict(startDate, endDate, districtId);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<TrendingNewspaperResponse> getTrendingNewspaperByStateByLanguage(Date startDate, Date endDate, String stateName, String language) {
        return trendingNewspaperRepository.getTrendingNewspaperByStateByLanguage(startDate, endDate, stateName, language);
    }

    public List<TrendingNewspaperResponse> getTrendingNewspaperByDistrictByLanguage(Date startDate, Date endDate, Long districtId, String language) {
        return trendingNewspaperRepository.getTrendingNewspaperByDistrictByLanguage(startDate, endDate, districtId, language);
    }

    public List<TrendingNewspaperResponse> getTopTenNewspapersByState(Date startDate, Date endDate, String stateName) {
        return trendingNewspaperRepository.getTopTenNewspapersByState(startDate, endDate, stateName);
    }

    public List<TrendingNewspaperResponse> getTopTenNewspapersByStateByLanguage(Date startDate, Date endDate, String stateName, String language) {
        return trendingNewspaperRepository.getTopTenNewspapersByStateByLanguage(startDate, endDate, stateName, language);
    }

    public List<TrendingNewspaperResponse> getTopDeclinedNewspaper(Date startDate, Date endDate) {
        return trendingNewspaperRepository.getTopDeclinedNewspaper(startDate, endDate);
    }

    public List<TrendingNewspaperResponse> getTopDeclinedNewspaperByLanguage(Date startDate, Date endDate, String language) {
        return trendingNewspaperRepository.getTopDeclinedNewspaperByLanguage(startDate, endDate, language);
    }
}
