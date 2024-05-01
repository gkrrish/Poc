package com.poc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.entity.UserDetails;
import com.poc.master.repository.IndianNewspaperLanguageRepository;
import com.poc.repository.UserDetailsRepository;

@Service
public class UserService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private IndianNewspaperLanguageRepository languageRepository;

	public boolean isExistingUser(String mobileNumber) {
		UserDetails userDetails = userDetailsRepository.findByMobileNumber(mobileNumber);
		return userDetails==null ? false : true;
	}

	public List<String> getAllLanguges() {
		List<String> allLanguageNames = languageRepository.findAllLanguageNames();
		System.out.println("***All Languages " + allLanguageNames.toString());
		return allLanguageNames;
	}

	public Optional<UserDetails> getUserDetails(Long userId) {
		return userDetailsRepository.findById(userId);
	}

}
