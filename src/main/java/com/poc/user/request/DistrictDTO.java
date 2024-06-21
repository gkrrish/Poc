package com.poc.user.request;

import lombok.Data;

@Data
public class DistrictDTO {
    private Long districtId;
    private String districtName;
    private Long stateId;
}