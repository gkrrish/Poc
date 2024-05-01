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

@Service
public class UserService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private IndianNewspaperLanguageRepository languageRepository;
	@Autowired
	private StateRepository stateRepository;

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

}
