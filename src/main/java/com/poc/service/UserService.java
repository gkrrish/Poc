package com.poc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.entity.UserDetails;
import com.poc.master.entity.State;
import com.poc.master.repository.IndianNewspaperLanguageRepository;
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
	private StateRepository stateRepository;

	public UserDetails notExistingUser(String mobileNumber) {
		return userDetailsRepository.findByMobileNumber(mobileNumber);
	}

	public List<String> getAllLanguges() {
		List<String> allLanguageNames = languageRepository.findAllLanguageNames();
		return allLanguageNames;
	}

	public Optional<UserDetails> getUserDetails(Long userId) {
		return userDetailsRepository.findById(userId);
	}

	public List<String> getAllStates() {
		return stateRepository.findAll().stream().map(State::getStateName).collect(Collectors.toList());
	}

	public ExistingUserDetails getSubscriptioinDetails(String mobileNumber) {
		UserDetails userDetails = userDetailsRepository.findByMobileNumber(mobileNumber);
		//control logic move here later
		List<Object[]> queryResults = userDetailsRepository.getUserDetailsByMobileNumber(mobileNumber);

        ExistingUserDetails existingUserDetails = new ExistingUserDetails();
        existingUserDetails.setMobileNumber(userDetails.getMobileNumber());

        List<Details> detailsList = queryResults.stream().map(row -> {
            Details details = new Details();
            details.setNewsPaperName((String) row[1]);
            details.setLanguage((String) row[2]);
            details.setState((String) row[3]);
            details.setDistrict((String) row[4]);
            details.setMandal((String) row[5]);
            details.setBatchTime((String) row[6]);
            details.setSubscriptionCharges((int) row[7]);
            details.setSubscriptionChargesPerMonth(String.valueOf(row[7])); 
            return details;
        }).collect(Collectors.toList());

        existingUserDetails.setDetails(detailsList);

        double totalSubscriptionCharges = detailsList.stream()
                .mapToDouble(Details::getSubscriptionCharges)
                .sum();
        existingUserDetails.setTotalSubscriptionCharges(totalSubscriptionCharges);
        
        return existingUserDetails;
	}

}
