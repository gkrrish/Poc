package com.poc.zadminservice.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class AdminDashboardDailyReportResponse {
	
	private Date todayDate;
	private Long toatalDistinctUsers;
    private Long totalReaders;
    private Long todayPositiveDeltaReaders;
    private Long todayNegativeDeltaReaders;
    private String state;
    private String district;
    private String mandal;
    private Long totalPositiveDeltaInState;
    private Long totalNegativeDeltaInState;
    private Long totalPositiveDeltaInDistrict;
    private Long totalNegativeDeltaInDistrict;
    private Long totalPositiveDeltaInMandal;
    private Long totalNegativeDeltaInMandal;
    private String preparableScheduledTime;

}
