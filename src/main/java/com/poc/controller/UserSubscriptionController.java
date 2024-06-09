package com.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.service.UserSubscriptionService;

@RestController
public class UserSubscriptionController {

	@Autowired
	UserSubscriptionService userSubscriptionService;

	@GetMapping("/distinct-stateswise-newspapers-by-languagename")
    public ResponseEntity<List<Object[]>> getDistinctNewsPapersOnStatesByLanguage(@RequestParam String languageName) {
        List<Object[]> states = userSubscriptionService.getDistinctStatesByLanguage(languageName);
        return ResponseEntity.ok(states);
    }
	
	@GetMapping("/distinct-newspapers-by-statename")
    public ResponseEntity<List<Object[]>> getDistinctNewspapersByStateName(@RequestParam String stateName) {
        List<Object[]> newspapers = userSubscriptionService.getDistinctNewspapersByStateName(stateName);
        return ResponseEntity.ok(newspapers);
    }
	
	@GetMapping("/distinct-state-by-newspaper")
    public ResponseEntity<List<Object[]>> getDistinctStatesByNewspaperName(@RequestParam String newspaperName) {
        List<Object[]> states = userSubscriptionService.getDistinctStatesByNewspaperName(newspaperName);
        return ResponseEntity.ok(states);
    }
	
	@PostMapping("/upsert-subscription")
    public ResponseEntity<String> upsertSubscription(
            @RequestParam String mobileNumber,
            @RequestParam String batchTime,
            @RequestParam String mandalName,
            @RequestParam String newspaperName) {
        
        userSubscriptionService.upsertUserSubscription(mobileNumber, batchTime, mandalName, newspaperName);
        return ResponseEntity.ok("Subscription updated/created successfully");
    }

}
