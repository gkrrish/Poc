package com.poc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.entity.UserDetails;
import com.poc.master.entity.Country;
import com.poc.master.entity.State;
import com.poc.master.repository.CountryRepository;
import com.poc.master.repository.DistrictRepository;
import com.poc.master.repository.IndianNewspaperLanguageRepository;
import com.poc.master.repository.MandalRepository;
import com.poc.master.repository.StateRepository;
import com.poc.repository.UserDetailsRepository;
import com.poc.response.Details;
import com.poc.response.ExistingUserDetails;

@Service
public class UserService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private IndianNewspaperLanguageRepository languageRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private MandalRepository mandalRepository;

	public List<String> getAllLanguges() {
		List<String> allLanguageNames = languageRepository.findAllLanguageNames();
		return allLanguageNames;
	}

	public Optional<UserDetails> getUserDetails(Long userId) {
		return userDetailsRepository.findById(userId);
	}

	public List<String> getAllStates(String countryName) {
		Country country = countryRepository.findByCountryName(countryName);
		return stateRepository.findAllByCountryId(country.getCountryId())
                .stream()
                .map(State::getStateName)
                .collect(Collectors.toList());
	}

	public ExistingUserDetails getSubscriptioinDetails(String mobileNumber) {
		UserDetails userDetails = userDetailsRepository.findByMobileNumber(mobileNumber);
		if(userDetails==null) {
			return new ExistingUserDetails();
		}
		List<Object[]> queryResults = userDetailsRepository.getUserDetailsByMobileNumber(mobileNumber);

        ExistingUserDetails existingUserDetails = new ExistingUserDetails();
        existingUserDetails.setMobileNumber(userDetails.getMobileNumber());

        List<Details> detailsList = existingUserConversion(queryResults);

        existingUserDetails.setDetails(detailsList);

        double totalSubscriptionCharges = detailsList.stream()
        		.mapToDouble(details -> details.getSubscriptionCharges().doubleValue()) 
                .sum();
        existingUserDetails.setTotalSubscriptionCharges(totalSubscriptionCharges);
        
        return existingUserDetails;
	}

	
	public List<String> getAllDistricts(String stateName) {
		return districtRepository.findAllDistrictNamesByStateName(stateName);
	}
	public List<String> getAllMandals(String districtName) {
		return mandalRepository.findAllMandalNamesByDistrictName(districtName);
	}
	
	private List<Details> existingUserConversion(List<Object[]> queryResults) {
		return queryResults.stream().map(row -> {
            Details details = new Details();
            details.setNewsPaperName((String) row[1]);
            details.setLanguage((String) row[2]);
            details.setState((String) row[3]);
            details.setDistrict((String) row[4]);
            details.setMandal((String) row[5]);
            details.setBatchTime((String) row[6]);
            details.setSubscriptionCharges((BigDecimal) row[7]);
            details.setSubscriptionChargesPerMonth((BigDecimal)(row[7])); 
            return details;
        }).collect(Collectors.toList());
	}

	


}
