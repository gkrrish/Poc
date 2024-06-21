package com.poc.user.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.master.entity.Vendor;
import com.poc.user.service.UserSubscriptionService;

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
		
		newspaperName = newspaperName.trim();
		mandalName = mandalName.trim();
        
        userSubscriptionService.upsertUserSubscription(mobileNumber, batchTime, mandalName, newspaperName,false);
        return ResponseEntity.ok("Subscription updated/created successfully");
    }
	
	@PostMapping("/unsubscription")
    public ResponseEntity<String> unSubscription(
            @RequestParam String mobileNumber,
            @RequestParam String batchTime,
            @RequestParam String mandalName,
            @RequestParam String newspaperName) {
		
		newspaperName = newspaperName.trim();
		mandalName = mandalName.trim();
        
        userSubscriptionService.upsertUserSubscription(mobileNumber, batchTime, mandalName, newspaperName, true);
        return ResponseEntity.ok("UnSubscription successfully");
    }
	
	
	@GetMapping("/vendors/{newspaperMasterId}/{locationId}")
    public Optional<Vendor> getVendors(@PathVariable Long newspaperMasterId, @PathVariable Long locationId) {
        return userSubscriptionService.getVendorsByNewspaperMasterIdAndLocationId(newspaperMasterId, locationId);
    }

}
