package com.poc.zdashboard.vendor.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.auser.master.repository.MasterNewspaperRepository;
import com.poc.zdashboard.vendor.repository.AdminDashboardCustomRepository;
import com.poc.zdashboard.vendor.response.AdminDashboardDailyReportResponse;

@Service
public class AdminDashboardService {

    @Autowired
    private AdminDashboardCustomRepository adminDashboardRepository;
    
    @Autowired
    private MasterNewspaperRepository masterNewspaperRepository;

    public AdminDashboardDailyReportResponse getDailyReport(String newspaperName) {
    	//make it util method masterNewspaerId
    	Long masterNewsPaperIdByNewspaperName = masterNewspaperRepository.findByNewspaperName(newspaperName).get().getId();
        int masterNewsPaperId = Long.valueOf(masterNewsPaperIdByNewspaperName).intValue();
    	
    	Long toatalDistinctUsers=adminDashboardRepository.findTotalDistinctReaders(masterNewsPaperId);
        Long totalReaders = adminDashboardRepository.findTotalReaders(masterNewsPaperId);
        Long todayPositiveDeltaReaders = adminDashboardRepository.findTodayPositiveDeltaReaders(masterNewsPaperId);
        Long todayNegativeDeltaReaders = adminDashboardRepository.findTodayNegativeDeltaReaders(masterNewsPaperId);
        
        String preparableScheduledTime = adminDashboardRepository.findMostScheduledBatchTimeNewspaper(masterNewsPaperId);

        return AdminDashboardDailyReportResponse.builder()
                .todayDate(new Date())
                .totalDistinctUsers(toatalDistinctUsers)
                .totalReaders(totalReaders)
                .todayPositiveDeltaReaders(todayPositiveDeltaReaders)
                .todayNegativeDeltaReaders(todayNegativeDeltaReaders)
                .preparableScheduledTime(preparableScheduledTime)
                .build();
    }
}
