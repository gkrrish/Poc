package com.poc.auser.request;

import lombok.Data;

@Data
public class StateDTO {
    private Long stateId;
    private String stateName;
    private Long countryId;
}