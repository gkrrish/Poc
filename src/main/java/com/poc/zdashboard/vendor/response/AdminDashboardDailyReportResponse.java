package com.poc.zdashboard.vendor.response;

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
	private Long totalDistinctUsers;
	private Long totalReaders;
	private Long todayPositiveDeltaReaders;
	private Long todayNegativeDeltaReaders;
	private String preparableScheduledTime;
}
