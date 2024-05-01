package com.poc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.poc.entity.UserDetails;
import com.poc.entity.UserSubscription;
import com.poc.request.WelcomeRequest;
import com.poc.response.WelcomeResponse;
import com.poc.service.UserService;
import com.poc.util.StringUtils;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/welcome") // fake charges?
	@ResponseStatus
	public ResponseEntity<?> welcomeUser(@RequestBody WelcomeRequest request) {

		if (userService.notExistingUser(request.getMobileNumber())) {
			WelcomeResponse welcomeResponse = new WelcomeResponse(StringUtils.WELCOME_MESSAGE);
			List<String> languages = userService.getAllLanguges();
			welcomeResponse.setLanguages(languages);

			return ResponseEntity.ok(welcomeResponse);

		} else {

		}

		return ResponseEntity.ok("welcome-okay");

	}
	
	@GetMapping("/states/{mobileNumber}")
	@ResponseStatus
	@ResponseBody
	public List<String> getStates(@PathVariable String mobileNumber){
		return userService.getAllStates();
	}
	
	@GetMapping("/test")
	public String getTest(){
		String test = userService.getTest();
		return test;
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
