package com.poc.zdashboard.vendor.response;

import java.util.List;

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
public class DistrictwiseAdminReportResponse {
    private String districtName;
    private Long districtwiseDistinctUsers;
    private Long districtwiseReaders;
    private Long totalPositiveDeltaInDistrict;
    private Long totalNegativeDeltaInDistrict;
    private String districtwisePreparableTime;
    private List<MandalwiseAdminReportResponse> mandals;
}