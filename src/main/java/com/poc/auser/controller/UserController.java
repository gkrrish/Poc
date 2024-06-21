package com.poc.auser.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.poc.auser.main.entity.UserDetails;
import com.poc.auser.master.entity.Vendor;
import com.poc.auser.master.entity.VendorDetails;
import com.poc.auser.request.CreateVendorDetailsRequest;
import com.poc.auser.request.CreateVendorRequest;
import com.poc.auser.request.WelcomeRequest;
import com.poc.auser.response.AvailableNewspapersByMandalwise;
import com.poc.auser.response.ExistingUserDetails;
import com.poc.auser.service.UserService;
import com.poc.auser.service.VendorService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
    private VendorService vendorService;

	@PostMapping("/welcome") // fake charges?
	@ResponseStatus
	public ResponseEntity<?> welcomeUser(@RequestBody WelcomeRequest request) throws IOException {
		return userService.processWelcomeRequest(request);
	}
	
	@GetMapping("/user-subscription-details-ui/{mobileNumber}")
	@ResponseStatus
	public ExistingUserDetails getUserSubscriptionDetailsForUI(@PathVariable String mobileNumber) throws IOException {
		return userService.getSubscriptioinDetails(mobileNumber);
	}
	
	 @GetMapping("/available-newspapers-by-mandal")
	    public AvailableNewspapersByMandalwise getAvailableNewspapersByMandalwise(@RequestParam String stateName,@RequestParam String districtName) {
	        return userService.getAvailableNewspapersByMandalwise(stateName, districtName);
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
		return userDetailsOptional.isPresent() ? ResponseEntity.ok(userDetailsOptional.get()) : ResponseEntity.notFound().build();

	}
	
	@GetMapping("/batchids/{timePeriod}")
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public List<String> getBatchIds(@PathVariable String timePeriod) {
        return userService.getDeliveryTimesByTimePeriod(timePeriod);
    }
	
	//admin-access
	@PostMapping("/create-vendor-details")
    public VendorDetails createVendorDetails(@RequestBody CreateVendorDetailsRequest request) {
        return vendorService.createVendorDetails(request);
    }
	
	// admin-access
	@PostMapping("/create-new-available-mandal-for-newspaper")
    public Vendor createVendor(@RequestBody CreateVendorRequest request) {
        return vendorService.createVendor(request);
    }
	
}
