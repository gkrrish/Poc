package com.poc.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.poc.master.repository.GenerateLocationNameRepository;

public class UtilMethods {

	@Autowired
	private GenerateLocationNameRepository locationRepository;

	public String generateLocationName(int stateId, int districtId, int mandalId) {
		return locationRepository.generateLocationName(stateId, districtId, mandalId);
	}

}
