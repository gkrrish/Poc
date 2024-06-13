package com.poc.zadminservice.repository;

import java.util.List;

public interface AdminDashboardCustomRepository {
	Long findTotalDistinctReaders(String newspaperName);
    Long findTotalReaders(String newspaperName);
    Long findTodayPositiveDeltaReaders(String newspaperName);
    Long findTodayNegativeDeltaReaders(String newspaperName);
    List<Object[]> findStatewiseDeltaReaders(String newspaperName);
    List<Object[]> findDistrictwiseDeltaReaders(String newspaperName);
    List<Object[]> findMandalwiseDeltaReaders(String newspaperName);
    String findMostAverageBatchTime();
}
