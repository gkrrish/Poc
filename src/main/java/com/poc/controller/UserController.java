package com.poc.controller;

import java.util.Optional;

import org.apache.catalina.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.entity.UserDetails;
import com.poc.repository.UserDetailsRepository;
import com.poc.request.WelcomeRequest;
import com.poc.response.WelcomeResponse;
import com.poc.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Autowired
	private UserService userService;

	@PostMapping("/welcome") // fake charges?
	public ResponseEntity<?> welcomeUser(@RequestBody WelcomeRequest request) {

		if (userDetailsRepository.findByMobileNumber(request.getMobileNumber()).getMobileNumber() == null) {
			WelcomeResponse welcomeResponse = new WelcomeResponse();
			welcomeResponse.setMessage("Welcome ! News on whatsApp service, we glad to hear! that you are intrested! please select the below newspapers we are going to send on scheuled time daily" );
			welcomeResponse.setLanguage(StringUtil.LANGUAGE_LIST);
			return ResponseEntity.ok(welcomeResponse);
			
		} else {
			userService.getSubscriptionDetails(request.getMobileNumber());
		}

		return ResponseEntity.ok("welcome-okay");

	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDetails> getUserDetails(@PathVariable Long userId) {
		Optional<UserDetails> userDetailsOptional = userDetailsRepository.findById(userId);
		if (userDetailsOptional.isPresent()) {
			return ResponseEntity.ok(userDetailsOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
