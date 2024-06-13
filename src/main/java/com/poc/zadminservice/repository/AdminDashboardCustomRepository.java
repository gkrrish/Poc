package com.poc.zadminservice.repository;

import java.util.List;

public interface AdminDashboardCustomRepository {
	Long findTotalDistinctReaders(int newspaperMasterId);
    Long findTotalReaders(int newspaperMasterId);
    Long findTodayPositiveDeltaReaders(int newspaperMasterId);
    Long findTodayNegativeDeltaReaders(int newspaperMasterId);
    List<Object[]> findStatewiseDeltaReaders(int newspaperMasterId);
    List<Object[]> findDistrictwiseDeltaReaders(int newspaperMasterId);
    List<Object[]> findMandalwiseDeltaReaders(int newspaperMasterId);
    String findMostScheduledBatchTimeNewspaper(int newspaperMasterId);
}
