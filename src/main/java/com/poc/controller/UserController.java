package com.poc.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.poc.helper.ResponseHelper;
import com.poc.main.entity.DailyBanner;
import com.poc.main.entity.UserDetails;
import com.poc.master.entity.Vendor;
import com.poc.master.entity.VendorDetails;
import com.poc.request.CreateVendorDetailsRequest;
import com.poc.request.CreateVendorRequest;
import com.poc.request.WelcomeRequest;
import com.poc.response.AvailableNewspapersByMandalwise;
import com.poc.response.BannerImageResponse;
import com.poc.response.ExistingUserDetails;
import com.poc.response.WelcomeResponse;
import com.poc.service.DailyBannerService;
import com.poc.service.UserService;
import com.poc.service.VendorService;

@RestController
public class UserController {
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
	@Autowired
    private VendorService vendorService;

	@Autowired
	private DailyBannerService dailyBannerService;
	
	@PostMapping("/welcome")  //Fake Charges ?
    public ResponseEntity<?> welcomeUser(@RequestBody WelcomeRequest request) {
        log.info("welcome request {}", request);
        try {
            WelcomeResponse welcomeResponse = userService.processWelcomeRequest(request);
            return ResponseHelper.createResponse(welcomeResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new WelcomeResponse("Failed to process request"));
        }
    }
	
	@GetMapping("/banner/{id}")
    public ResponseEntity<?> getBannerById(@PathVariable Integer id) throws IOException {
        Optional<DailyBanner> dailyBannerOptional = dailyBannerService.getBannerById(id);

        if (dailyBannerOptional.isPresent()) {
            DailyBanner dailyBanner = dailyBannerOptional.get();
            String bannerFilePath = dailyBannerService.saveBannerImage(dailyBanner.getBannerFilePath());
            BannerImageResponse response = new BannerImageResponse(bannerFilePath);
            return ResponseHelper.createImageResponse(response.getImageBase64());
        } else {
        	 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new WelcomeResponse("Failed to process request"));
        }
    }
	
	@GetMapping("/banner-by-location/{locationId}/{newspaperId}")
    public ResponseEntity<?> getBannerByLocationAndNewspaper(@PathVariable Long locationId, @PathVariable Long newspaperId) throws IOException {
        Optional<DailyBanner> dailyBannerOptional = dailyBannerService.getBannerByLocationAndNewspaper(locationId, newspaperId);

        if (dailyBannerOptional.isPresent()) {
            DailyBanner dailyBanner = dailyBannerOptional.get();
            String bannerFilePath = dailyBannerService.saveBannerImage(dailyBanner.getBannerFilePath());
            BannerImageResponse response = new BannerImageResponse(bannerFilePath);
            return ResponseHelper.createImageResponse(response.getImageBase64());
        } else {
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new WelcomeResponse("Failed to process request"));
        }
    }

	@GetMapping("/languages")
	public List<String> getAllLanguages() {
		return userService.getAllLanguges();
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
