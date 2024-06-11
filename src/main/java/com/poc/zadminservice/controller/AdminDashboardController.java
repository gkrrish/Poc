package com.poc.zadminservice.controller;

import com.poc.zadminservice.response.AdminDashboardDailyReportResponse;
import com.poc.zadminservice.service.AdminDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService service;

    @GetMapping("/admin/dashboard/daily-report")
    public AdminDashboardDailyReportResponse getDailyReport(@RequestParam String newspaperName) {
        return service.getDailyReport(newspaperName);
    }
}
