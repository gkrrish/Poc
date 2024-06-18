package com.poc.z.newspaper.adminservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.master.repository.MasterNewspaperRepository;
import com.poc.service.UserSubscriptionService;
import com.poc.z.newspaper.adminservice.repository.CustomStateDashboardReportRepository;
import com.poc.z.newspaper.adminservice.response.StatewiseAdminReportResponse;

@Service
public class AdminDashboardDistrictService {
	
	@Autowired
    private CustomStateDashboardReportRepository customStateDashboardReportRepository;
	@Autowired
    private MasterNewspaperRepository masterNewspaperRepository;
	@Autowired
	private UserSubscriptionService userSubscriptionService;

    public List<StatewiseAdminReportResponse> getStatewiseReport(String newspaperName, Date subscriptionStartDate, Date searchSubscriptionUntilDate, Date subscriptionFromDate, Date subscriptionEndDate) {
    	
    	Long masterNewsPaperIdByNewspaperName = masterNewspaperRepository.findByNewspaperName(newspaperName).get().getId();
        int newspaperMasterId = Long.valueOf(masterNewsPaperIdByNewspaperName).intValue();
    	
		List<Object[]> states = userSubscriptionService.getDistinctStatesByNewspaperName(newspaperName);
        List<String> stateNames = states.stream().map(state -> (String) state[1]).collect(Collectors.toList());
    	
        List<StatewiseAdminReportResponse> statewiseReportResponses = new ArrayList<>();

        for (String stateName : stateNames) {
            Long distinctUsers = customStateDashboardReportRepository.findStatewiseDistinctUsers(stateName, newspaperMasterId);
            Long readers = customStateDashboardReportRepository.findStatewiseReaders(stateName, newspaperMasterId);
            Long positiveDelta = customStateDashboardReportRepository.findTotalPositiveDeltaInState(stateName, newspaperMasterId,subscriptionStartDate,searchSubscriptionUntilDate);
            Long negativeDelta = customStateDashboardReportRepository.findTotalNegativeDeltaInState(stateName, newspaperMasterId,subscriptionFromDate,subscriptionEndDate);
            String statewiseScheduledTime = customStateDashboardReportRepository.findMostScheduledBatchTimeNewspaperInStatewise(stateName, newspaperMasterId);
            

            StatewiseAdminReportResponse response = StatewiseAdminReportResponse.builder()
                    .stateName(stateName)
                    .statewiseDistinctUsers(distinctUsers)
                    .statewiseReaders(readers)
                    .totalPositiveDeltaInState(positiveDelta)
                    .totalNegativeDeltaInState(negativeDelta)
                    .statewisePreparableTime(statewiseScheduledTime)
                    .build();

            statewiseReportResponses.add(response);
        }

        return statewiseReportResponses;
    }

}
