package com.poc.z.newspaper.adminservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.z.newspaper.adminservice.response.AdminDashboardDailyReportResponse;
import com.poc.z.newspaper.adminservice.response.StatewiseAdminReportResponse;
import com.poc.z.newspaper.adminservice.service.AdminDashboardDistrictService;
import com.poc.z.newspaper.adminservice.service.AdminDashboardService;

@RestController
public class AdminDashboardController {

	@Autowired
	private AdminDashboardService service;
	
	@Autowired
    private AdminDashboardDistrictService adminDashboardDistrictService;

	@GetMapping("/admin/dashboard/daily-report")
	public AdminDashboardDailyReportResponse getDailyReport(@RequestParam String newspaperName) {
		return service.getDailyReport(newspaperName);
	}
	
	@GetMapping("/statewise-report")
    public List<StatewiseAdminReportResponse> getStatewiseReport(@RequestParam String newspaperName) {
        return adminDashboardDistrictService.getStatewiseReport(newspaperName);
    }

}
