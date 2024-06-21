package com.poc.zdashboard.vendor.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.auser.master.repository.MasterNewspaperRepository;
import com.poc.zdashboard.vendor.repository.CustomMandalDashboardReportRepository;
import com.poc.zdashboard.vendor.response.MandalwiseAdminReportResponse;

@Service
public class AdminDashboardMandalwiseService {

    @Autowired
    private CustomMandalDashboardReportRepository customMandalDashboardReportRepository;

    @Autowired
    private MasterNewspaperRepository masterNewspaperRepository;

    public Map<String, List<MandalwiseAdminReportResponse>> getMandalwiseReport(String stateName, String newspaperName, Date subscriptionStartDate, 
                                                                                Date searchSubscriptionUntilDate, Date subscriptionFromDate, Date subscriptionEndDate) {
        Map<String, List<MandalwiseAdminReportResponse>> districtNameKeyAndMandalsReportList = new HashMap<>();

        Long masterNewspaperId = masterNewspaperRepository.findByNewspaperName(newspaperName).get().getId();
        int newspaperMasterId = Long.valueOf(masterNewspaperId).intValue();
        
        List<String> districtNames = customMandalDashboardReportRepository.getDistrictsByStateAndNewspaperMasterId(stateName, newspaperMasterId);

        for (String districtName : districtNames) {
            List<String> mandals = customMandalDashboardReportRepository.findMandalsByDistrictAndNewspaperMasterId(districtName, newspaperMasterId);
            List<MandalwiseAdminReportResponse> mandalwiseReportResponses = new ArrayList<>();

            for (String mandalName : mandals) {
                Long distinctUsers = customMandalDashboardReportRepository.findMandalwiseDistinctUsers(mandalName, newspaperMasterId);
                Long readers = customMandalDashboardReportRepository.findMandalwiseReaders(mandalName, newspaperMasterId);
                Long positiveDelta = customMandalDashboardReportRepository.findTotalPositiveDeltaInMandal(mandalName, newspaperMasterId, subscriptionStartDate, searchSubscriptionUntilDate);
                Long negativeDelta = customMandalDashboardReportRepository.findTotalNegativeDeltaInMandal(mandalName, newspaperMasterId, subscriptionFromDate, subscriptionEndDate);
                String mandalwiseScheduledTime = customMandalDashboardReportRepository.findMostScheduledBatchTimeNewspaperInMandal(mandalName, newspaperMasterId);

                MandalwiseAdminReportResponse response = MandalwiseAdminReportResponse.builder()
                        .mandalName(mandalName)
                        .mandalwiseDistinctUsers(distinctUsers)
                        .mandalwiseReaders(readers)
                        .totalPositiveDeltaInMandal(positiveDelta)
                        .totalNegativeDeltaInMandal(negativeDelta)
                        .mandalwisePreparableTime(mandalwiseScheduledTime)
                        .build();

                mandalwiseReportResponses.add(response);
            }
            districtNameKeyAndMandalsReportList.put(districtName, mandalwiseReportResponses);
        }

        return districtNameKeyAndMandalsReportList;
    }
}