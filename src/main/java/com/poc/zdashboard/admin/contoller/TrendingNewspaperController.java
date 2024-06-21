package com.poc.zdashboard.admin.contoller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.zdashboard.admin.response.TrendingNewspaperResponse;
import com.poc.zdashboard.admin.service.TrendingNewspaperService;

@RestController
public class TrendingNewspaperController {

	@Autowired
	private TrendingNewspaperService trendingNewspaperService;

	@GetMapping("/trending-newspaper")
	public TrendingNewspaperResponse getTrendingNewspaper(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		
		return trendingNewspaperService.getTrendingNewspaper(startDate, endDate);
	}

	@GetMapping("/trending-newspaper-by-language")
	public TrendingNewspaperResponse getTrendingNewspaperByLanguage(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String language) {
		
		return trendingNewspaperService.getTrendingNewspaperByLanguage(startDate, endDate, language.trim());
	}

	@GetMapping("/top-ten-trending-newspapers-by-language")
	public List<TrendingNewspaperResponse> getTopTenTrendingNewspapersByLanguage(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String language) {
		
		return trendingNewspaperService.getTopTenTrendingNewspapersByLanguage(startDate, endDate, language.trim());
	}

	@GetMapping("/trending-newspaper-by-state")
	public TrendingNewspaperResponse getTrendingNewspaperByState(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String stateName) {
		
		return trendingNewspaperService.getTrendingNewspaperByState(startDate, endDate, stateName.trim());
	}

	@GetMapping("/trending-newspaper-by-district")
	public TrendingNewspaperResponse getTrendingNewspaperByDistrict(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam Long districtId) {
		
		return trendingNewspaperService.getTrendingNewspaperByDistrict(startDate, endDate, districtId);
	}

	@GetMapping("/trending-newspaper-by-state-by-language")
	public List<TrendingNewspaperResponse> getTrendingNewspaperByStateByLanguage(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String stateName,
			@RequestParam String language) {
		
		return trendingNewspaperService.getTrendingNewspaperByStateByLanguage(startDate, endDate, stateName.trim(),language.trim());
	}

	@GetMapping("/trending-newspaper-by-district-by-language")
	public List<TrendingNewspaperResponse> getTrendingNewspaperByDistrictByLanguage(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam Long districtId,
			@RequestParam String language) {
		
		return trendingNewspaperService.getTrendingNewspaperByDistrictByLanguage(startDate, endDate, districtId,language.trim());
	}

	@GetMapping("/top-ten-newspapers-by-state")
	public List<TrendingNewspaperResponse> getTopTenNewspapersByState(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String stateName) {
		
		return trendingNewspaperService.getTopTenNewspapersByState(startDate, endDate, stateName.trim());
	}

	@GetMapping("/top-ten-newspapers-by-state-by-language")
	public List<TrendingNewspaperResponse> getTopTenNewspapersByStateByLanguage(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String stateName,
			@RequestParam String language) {
		
		return trendingNewspaperService.getTopTenNewspapersByStateByLanguage(startDate, endDate, stateName.trim(),
				language.trim());
	}

	@GetMapping("/top-declined-newspaper")
	public List<TrendingNewspaperResponse> getTopDeclinedNewspaper(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		
		return trendingNewspaperService.getTopDeclinedNewspaper(startDate, endDate);
	}

	@GetMapping("/top-declined-newspaper-by-language")
	public List<TrendingNewspaperResponse> getTopDeclinedNewspaperByLanguage(
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate, @RequestParam String language) {
		
		return trendingNewspaperService.getTopDeclinedNewspaperByLanguage(startDate, endDate, language.trim());
	}
}
