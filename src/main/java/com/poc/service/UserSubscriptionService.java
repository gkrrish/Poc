package com.poc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.master.repository.MasterNewspaperRepository;
import com.poc.master.repository.VendorRepository;

@Service
public class UserSubscriptionService {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	@Autowired
	private MasterNewspaperRepository masterNewspaperRepository;
	
	public List<Object[]> getDistinctStatesByLanguage(String languageName) {
        return vendorRepository.findDistinctStatesByLanguage(languageName);
    }
	
	public List<Object[]> getDistinctNewspapersByStateName(String stateName) {
        return masterNewspaperRepository.findDistinctNewspapersByStateName(stateName);
    }

}
