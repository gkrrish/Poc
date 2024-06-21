package com.poc.auser.request;

import lombok.Data;

@Data
public class StatewiseLocationDTO {
    private Long locationId;
    private String locationName;
    private Long countryId;
    private Long stateId;
    private Long districtId;
    private Long mandalId;
}
