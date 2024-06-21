package com.poc.auser.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BatchTimePeriod {
    MORNING(1L, 13L),
    AFTERNOON(14L, 23L),
    EVENING(24L, 30L),
    NIGHT(31L, 39L);

    private final Long startId;
    private final Long endId;

    public static BatchTimePeriod fromString(String timePeriod) {
        switch (timePeriod.toLowerCase()) {
            case "morning": return MORNING;
            case "afternoon": return AFTERNOON;
            case "evening": return EVENING;
            case "night":  return NIGHT;
            default:throw new IllegalArgumentException("Invalid time period: " + timePeriod);
        }
    }
}