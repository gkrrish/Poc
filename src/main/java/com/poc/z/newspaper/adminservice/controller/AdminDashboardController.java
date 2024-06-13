package com.poc.z.newspaper.adminservice.controller;

import com.poc.z.newspaper.adminservice.response.AdminDashboardDailyReportResponse;
import com.poc.z.newspaper.adminservice.service.AdminDashboardService;

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
        //alter this logic to not specific to today, write utilize this controller to take the from date and to date, so that utilize this logic for period of logic
        /*
         * District wise prfarable time, 
         * statewise prefarable time
         * mandal wise preparable time
         * 
         * 
         */
    }
}
