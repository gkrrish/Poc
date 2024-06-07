package com.poc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.master.repository.VendorRepository;

@Service
public class UserSubscriptionService {
	
	@Autowired
	private VendorRepository vendorRepository;
	
	public List<Object[]> getDistinctStatesByLanguage(String languageName) {
        return vendorRepository.findDistinctStatesByLanguage(languageName);
    }

}
