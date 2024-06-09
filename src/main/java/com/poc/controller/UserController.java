package com.poc.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.poc.entity.UserDetails;
import com.poc.request.WelcomeRequest;
import com.poc.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/welcome") // fake charges?
	@ResponseStatus
	public ResponseEntity<?> welcomeUser(@RequestBody WelcomeRequest request) throws IOException {
		return userService.processWelcomeRequest(request);
	}

	@GetMapping("/states/{mobileNumber}")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<String> getStates(@PathVariable String mobileNumber) {
		return userService.getAllStates("India");
	}

	@GetMapping("/districts/{mobileNumber}/{stateName}")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<String> getDistricts(@PathVariable String mobileNumber, @PathVariable String stateName) {
		return userService.getAllDistricts(stateName);
	}
	
	@GetMapping("/mandals/{mobileNumber}/{districtName}")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<String> getMandals(@PathVariable String mobileNumber, @PathVariable String districtName) {
		return userService.getAllMandals(districtName);
	}
	
	
	@GetMapping("/scheduledtime/{mobileNumber}")
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public List<String> getBatchTimes(@PathVariable String mobileNumber) {
		return userService.getAllDeliveryTimes();
	}
	

	@GetMapping("/{userId}")
	public ResponseEntity<UserDetails> getUserDetails(@PathVariable Long userId) {
		Optional<UserDetails> userDetailsOptional = userService.getUserDetails(userId);
		if (userDetailsOptional.isPresent()) {
			return ResponseEntity.ok(userDetailsOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
}
/*
SELECT constraint_name, table_name 
FROM user_constraints 
WHERE r_constraint_name = 'SYS_C007023';

ALTER TABLE NEWSPAPER_FILES
DROP CONSTRAINT SYS_C007030;

ALTER TABLE VENDORS
DROP CONSTRAINT SYS_C007023;

ALTER TABLE VENDORS
ADD CONSTRAINT vendors_pk PRIMARY KEY (newspaper_id, location_id, newspaper_master_id);

ALTER TABLE USER_SUBSCRIPTION
ADD CONSTRAINT SYS_C007033 FOREIGN KEY (newspaper_id, location_id, newspaper_master_id) REFERENCES VENDORS(newspaper_id, location_id, newspaper_master_id);


ALTER TABLE USER_SUBSCRIPTION
ADD location_id INT
ADD newspaper_master_id INT;

ALTER TABLE USER_SUBSCRIPTION
ADD CONSTRAINT fk_user_subscription_vendors FOREIGN KEY (newspaper_id, location_id, newspaper_master_id)
REFERENCES VENDORS(newspaper_id, location_id, newspaper_master_id);


ALTER TABLE NEWSPAPER_FILES
ADD CONSTRAINT SYS_C007030 FOREIGN KEY (newspaper_id, location_id, newspaper_master_id) REFERENCES VENDORS(newspaper_id, location_id, newspaper_master_id);



ALTER TABLE NEWSPAPER_FILES
ADD location_id INT
ADD newspaper_master_id INT;




ALTER TABLE NEWSPAPER_FILES
ADD CONSTRAINT fk_newspaper_files_vendors FOREIGN KEY (newspaper_id, location_id, newspaper_master_id)
REFERENCES VENDORS(newspaper_id, location_id, newspaper_master_id);*/