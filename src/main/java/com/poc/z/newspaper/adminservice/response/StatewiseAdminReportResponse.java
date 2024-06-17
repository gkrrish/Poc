package com.poc.z.newspaper.adminservice.response;

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
public class StatewiseAdminReportResponse {
    private String stateName;
    private Long statewiseDistinctUsers;
    private Long statewiseReaders;
    private Long totalPositiveDeltaInState;
    private Long totalNegativeDeltaInState;
    private String statewisePreparableTime;
    private List<DistrictwiseAdminReportResponse> districts;
}

