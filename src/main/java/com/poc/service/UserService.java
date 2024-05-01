package com.poc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.entity.UserDetails;
import com.poc.master.entity.State;
import com.poc.master.entity.Vendor;
import com.poc.master.entity.VendorDetails;
import com.poc.master.repository.BatchJobRepository;
import com.poc.master.repository.CategoryTypeRepository;
import com.poc.master.repository.CountryRepository;
import com.poc.master.repository.DistrictRepository;
import com.poc.master.repository.IndianNewspaperLanguageRepository;
import com.poc.master.repository.MandalRepository;
import com.poc.master.repository.StateRepository;
import com.poc.master.repository.StatewiseLocationRepository;
import com.poc.master.repository.SubscriptionTypeRepository;
import com.poc.master.repository.VendorDetailsRepository;
import com.poc.master.repository.VendorRepository;
import com.poc.repository.UserDetailsRepository;

@Service
public class UserService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private IndianNewspaperLanguageRepository languageRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private MandalRepository mandalRepository;
	@Autowired
	private StatewiseLocationRepository statewiseLocationRepository;
	@Autowired
	private BatchJobRepository batchJobRepository;
	@Autowired
	private SubscriptionTypeRepository subscriptionTypeRepository;
	@Autowired
	private CategoryTypeRepository categoryTypeRepository;
	@Autowired
	private VendorDetailsRepository vendorDetailsRepository;
	@Autowired
	private VendorRepository vendorRepository;

	public boolean notExistingUser(String mobileNumber) {
		UserDetails userDetails = userDetailsRepository.findByMobileNumber(mobileNumber);
		return userDetails==null ? true : false;
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

	public List<Vendor> getTest() {
		return vendorRepository.findAll();
	}

}
