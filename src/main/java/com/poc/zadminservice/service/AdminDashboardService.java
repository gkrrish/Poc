package com.poc.zadminservice.service;

import com.poc.zadminservice.repository.AdminDashboardRepository;
import com.poc.zadminservice.repository.AdminDashboardCustomRepository;
import com.poc.zadminservice.response.AdminDashboardDailyReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminDashboardService {

    @Autowired
    private AdminDashboardRepository repository;

    @Autowired
    private AdminDashboardCustomRepository customRepository;

    public AdminDashboardDailyReportResponse getDailyReport(String newspaperName) {
        Long totalReaders = customRepository.findTotalReaders(newspaperName);
        Long todayPositiveDeltaReaders = customRepository.findTodayPositiveDeltaReaders(newspaperName);
        Long todayNegativeDeltaReaders = customRepository.findTodayNegativeDeltaReaders(newspaperName);
        
        List<Object[]> statewiseDeltas = customRepository.findStatewiseDeltaReaders(newspaperName);
        String state = statewiseDeltas.isEmpty() ? null : (String) statewiseDeltas.get(0)[0];
        Long totalPositiveDeltaInState = statewiseDeltas.isEmpty() ? 0L : ((Number) statewiseDeltas.get(0)[1]).longValue();
        Long totalNegativeDeltaInState = statewiseDeltas.isEmpty() ? 0L : ((Number) statewiseDeltas.get(0)[2]).longValue();
        
        List<Object[]> districtwiseDeltas = customRepository.findDistrictwiseDeltaReaders(newspaperName);
        String district = districtwiseDeltas.isEmpty() ? null : (String) districtwiseDeltas.get(0)[0];
        Long totalPositiveDeltaInDistrict = districtwiseDeltas.isEmpty() ? 0L : ((Number) districtwiseDeltas.get(0)[1]).longValue();
        Long totalNegativeDeltaInDistrict = districtwiseDeltas.isEmpty() ? 0L : ((Number) districtwiseDeltas.get(0)[2]).longValue();
        
        List<Object[]> mandalwiseDeltas = customRepository.findMandalwiseDeltaReaders(newspaperName);
        String mandal = mandalwiseDeltas.isEmpty() ? null : (String) mandalwiseDeltas.get(0)[0];
        Long totalPositiveDeltaInMandal = mandalwiseDeltas.isEmpty() ? 0L : ((Number) mandalwiseDeltas.get(0)[1]).longValue();
        Long totalNegativeDeltaInMandal = mandalwiseDeltas.isEmpty() ? 0L : ((Number) mandalwiseDeltas.get(0)[2]).longValue();

        String preparableScheduledTime = customRepository.findMostAverageBatchTime();

        return AdminDashboardDailyReportResponse.builder()
                .todayDate(new Date())
                .totalReaders(totalReaders)
                .todayPositiveDeltaReaders(todayPositiveDeltaReaders)
                .todayNegativeDeltaReaders(todayNegativeDeltaReaders)
                .state(state)
                .totalPositiveDeltaInState(totalPositiveDeltaInState)
                .totalNegativeDeltaInState(totalNegativeDeltaInState)
                .district(district)
                .totalPositiveDeltaInDistrict(totalPositiveDeltaInDistrict)
                .totalNegativeDeltaInDistrict(totalNegativeDeltaInDistrict)
                .mandal(mandal)
                .totalPositiveDeltaInMandal(totalPositiveDeltaInMandal)
                .totalNegativeDeltaInMandal(totalNegativeDeltaInMandal)
                .preparableScheduledTime(preparableScheduledTime)
                .build();
    }
}
