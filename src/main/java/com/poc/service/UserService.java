package com.poc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.entity.UserDetails;
import com.poc.repository.UserDetailsRepository;

@Service
public class UserService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	public UserDetails getSubscriptionDetails(String mobileNumber) {

		UserDetails byMobileNumber = userDetailsRepository.findByMobileNumber(mobileNumber);
		if (byMobileNumber != null) {
			List<Object[]> x = userDetailsRepository.findSubscriptionDetailsByMobileNumber(mobileNumber);
			
			for (Object[] result : x) {
			    String mobileNumber1 = (String) result[0];
			    String newspaperName = (String) result[1];
			    String batchTime = (String) result[2];

			    System.out.println("Mobile Number: " + mobileNumber1);
			    System.out.println("Newspaper Name: " + newspaperName);
			    System.out.println("Batch Time: " + batchTime);
			    System.out.println(); // Add a blank line for separation
			}
			
		}

		return null;
	}

}
