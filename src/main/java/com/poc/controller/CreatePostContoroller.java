package com.poc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.poc.main.entity.UserDetails;
import com.poc.main.entity.UserSubscription;
import com.poc.master.entity.District;
import com.poc.master.entity.IndianNewspaperLanguage;
import com.poc.master.entity.Mandal;
import com.poc.master.entity.State;
import com.poc.master.entity.StatewiseLocation;
import com.poc.request.DistrictDTO;
import com.poc.request.MandalDTO;
import com.poc.request.NewspaperLanguageDTO;
import com.poc.request.StateDTO;
import com.poc.request.StatewiseLocationDTO;
import com.poc.request.UserSubscriptionDTO;
import com.poc.service.CreatePostService;

@RestController
public class CreatePostContoroller {

	@Autowired
	private CreatePostService createPostService;

	@PostMapping("/create-state")
	public State createState(@RequestBody StateDTO stateDTO) {
		return createPostService.createState(stateDTO);
	}

	@PostMapping("/create-district")
	public District createDistrict(@RequestBody DistrictDTO districtDTO, StateDTO stateDTO) {
		return createPostService.createDistrict(districtDTO,stateDTO);
	}

	@PostMapping("/create-mandal")
	public Mandal createMandal(@RequestBody MandalDTO mandalDTO,DistrictDTO districtDTO, StateDTO stateDTO ) {
		return createPostService.createMandal(mandalDTO, districtDTO, stateDTO);
	}
	
	@PostMapping("/create-newspaperlanguages")
	public IndianNewspaperLanguage createNewspaperLanguage(@RequestBody NewspaperLanguageDTO newspaperLanguageDTO) {
		return createPostService.createNewspaperLanguage(newspaperLanguageDTO);
	}


	@PostMapping("/create-statewiselocation")
	public StatewiseLocation createStatewiseLocation(@RequestBody StatewiseLocationDTO statewiseLocationDTO) {
		return createPostService.createStatewiseLocation(statewiseLocationDTO);
	}

	@PostMapping("/create-new-user")
	public UserDetails createUserDetails(@RequestBody UserDetails userDetails) {
		return createPostService.createUserDetails(userDetails);
	}

	@PostMapping("/create-user-subscription")
	public UserSubscription createSubscription(@RequestBody UserSubscriptionDTO subscriptionDTO) {
		return createPostService.createUserSubscription(subscriptionDTO);
	}
}
