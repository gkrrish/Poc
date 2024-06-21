package com.poc.zdashboard.vendor.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.zdashboard.vendor.response.AdminDashboardDailyReportResponse;
import com.poc.zdashboard.vendor.response.DistrictwiseAdminReportResponse;
import com.poc.zdashboard.vendor.response.MandalwiseAdminReportResponse;
import com.poc.zdashboard.vendor.response.StatewiseAdminReportResponse;
import com.poc.zdashboard.vendor.service.AdminDashboardDistrictwiseService;
import com.poc.zdashboard.vendor.service.AdminDashboardMandalwiseService;
import com.poc.zdashboard.vendor.service.AdminDashboardService;
import com.poc.zdashboard.vendor.service.AdminDashboardStatewiseService;

@RestController
public class AdminDashboardController {

	@Autowired
	private AdminDashboardService service;

	@Autowired
	private AdminDashboardStatewiseService adminDashboardDistrictService;
	
	@Autowired
	AdminDashboardDistrictwiseService adminDashboardDistrictwiseService;
	
	@Autowired
	AdminDashboardMandalwiseService adminDashboardMandalwiseService;

	@GetMapping("/admin/dashboard/daily-report")
	public AdminDashboardDailyReportResponse getDailyReport(@RequestParam String newspaperName) {
		return service.getDailyReport(newspaperName);
	}

	@GetMapping("/statewise-report")
	public List<StatewiseAdminReportResponse> getStatewiseReport(
			@RequestParam String newspaperName,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionStartDate,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchSubscriptionUntilDate,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionFromDate,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionEndDate) {
	    
		return adminDashboardDistrictService.getStatewiseReport(newspaperName,subscriptionStartDate,searchSubscriptionUntilDate,subscriptionFromDate,subscriptionEndDate);
	}
	
	@GetMapping("/districtwise-report")
	public Map<String, List<DistrictwiseAdminReportResponse>> getDistrictwiseReport(
			@RequestParam String newspaperName,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionStartDate,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchSubscriptionUntilDate,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionFromDate,
	        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionEndDate) {
	    
		return adminDashboardDistrictwiseService.getDistrictwiseReport(newspaperName,subscriptionStartDate,searchSubscriptionUntilDate,subscriptionFromDate,subscriptionEndDate);
	}
	
	@GetMapping("/mandalwise-report")
    public Map<String, List<MandalwiseAdminReportResponse>> getMandalwiseReport(
    		@RequestParam String stateName,
            @RequestParam String newspaperName,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionStartDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date searchSubscriptionUntilDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionFromDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date subscriptionEndDate) {

        return adminDashboardMandalwiseService.getMandalwiseReport(stateName, newspaperName, subscriptionStartDate, searchSubscriptionUntilDate, subscriptionFromDate, subscriptionEndDate);
    }

}
