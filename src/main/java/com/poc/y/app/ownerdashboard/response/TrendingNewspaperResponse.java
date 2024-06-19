package com.poc.y.app.ownerdashboard.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendingNewspaperResponse {
    private String newspaperName;
    private String language;
    private String stateName;
    private String districtName;
    private Long subscriberCount;
}