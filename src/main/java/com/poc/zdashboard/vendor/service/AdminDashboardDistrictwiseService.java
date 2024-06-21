package com.poc.zdashboard.vendor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.auser.master.repository.MasterNewspaperRepository;
import com.poc.auser.service.UserSubscriptionService;
import com.poc.zdashboard.vendor.repository.CustomDistrictDashboardReportRepository;
import com.poc.zdashboard.vendor.response.DistrictwiseAdminReportResponse;

@Service
public class AdminDashboardDistrictwiseService {
    
    @Autowired
    private CustomDistrictDashboardReportRepository customDistrictDashboardReportRepository;
    
    @Autowired
    private MasterNewspaperRepository masterNewspaperRepository;
    
    @Autowired
    private UserSubscriptionService userSubscriptionService;

	public Map<String, List<DistrictwiseAdminReportResponse>> getDistrictwiseReport(String newspaperName, Date subscriptionStartDate, 
																					Date searchSubscriptionUntilDate, Date subscriptionFromDate, Date subscriptionEndDate) {
        Map<String, List<DistrictwiseAdminReportResponse>> stateNameKeyAndDistrictsReportList=new HashMap<>();
    	
    	Long masterNewsPaperIdByNewspaperName = masterNewspaperRepository.findByNewspaperName(newspaperName).get().getId();
        int newspaperMasterId = Long.valueOf(masterNewsPaperIdByNewspaperName).intValue();
        
        List<Object[]> states = userSubscriptionService.getDistinctStatesByNewspaperName(newspaperName);
        List<String> stateNames = states.stream().map(state -> (String) state[1]).collect(Collectors.toList());
        
        for (String stateName : stateNames) {
        	List<String> districts = customDistrictDashboardReportRepository.findDistrictsByStateAndNewspaperMasterId(stateName, newspaperMasterId);
        	List<String> districtNames = districts.stream().collect(Collectors.toList());
        	
        List<DistrictwiseAdminReportResponse> districtwiseReportResponses = new ArrayList<>();

        for (String districtName : districtNames) {
            Long distinctUsers = customDistrictDashboardReportRepository.findDistrictwiseDistinctUsers(districtName, newspaperMasterId);
            Long readers = customDistrictDashboardReportRepository.findDistrictwiseReaders(districtName, newspaperMasterId);
            Long positiveDelta = customDistrictDashboardReportRepository.findTotalPositiveDeltaInDistrict(districtName, newspaperMasterId, subscriptionStartDate, searchSubscriptionUntilDate);
            Long negativeDelta = customDistrictDashboardReportRepository.findTotalNegativeDeltaInDistrict(districtName, newspaperMasterId, subscriptionFromDate, subscriptionEndDate);
            String districtwiseScheduledTime = customDistrictDashboardReportRepository.findMostScheduledBatchTimeNewspaperInDistrict(districtName, newspaperMasterId);
            
            DistrictwiseAdminReportResponse response = DistrictwiseAdminReportResponse.builder()
                    .districtName(districtName)
                    .districtwiseDistinctUsers(distinctUsers)
                    .districtwiseReaders(readers)
                    .totalPositiveDeltaInDistrict(positiveDelta)
                    .totalNegativeDeltaInDistrict(negativeDelta)
                    .districtwisePreparableTime(districtwiseScheduledTime)
                    .build();

            districtwiseReportResponses.add(response);
        }
        stateNameKeyAndDistrictsReportList.put(stateName, districtwiseReportResponses);
       }
        return stateNameKeyAndDistrictsReportList;
    }
}
