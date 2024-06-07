package com.poc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.service.UserSubscriptionService;

@RestController
public class UserSubscriptionController {

	@Autowired
	UserSubscriptionService userSubscriptionService;

	@GetMapping("/distinct-states")
    public ResponseEntity<List<Object[]>> getDistinctStatesByLanguage(@RequestParam String languageName) {
        List<Object[]> states = userSubscriptionService.getDistinctStatesByLanguage(languageName);
        return ResponseEntity.ok(states);
    }

}
