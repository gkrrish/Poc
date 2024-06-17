package com.poc.z.newspaper.adminservice.response;

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
public class MandalwiseAdminReportResponse {
    private String mandalName;
    private Long mandalwiseDistinctUsers;
    private Long mandalwiseReaders;
    private Long totalPositiveDeltaInMandal;
    private Long totalNegativeDeltaInMandal;
    private String mandalwisePreparableTime;
}
