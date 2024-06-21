package com.poc.auser.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poc.auser.main.entity.UserDetails;
import com.poc.auser.main.entity.UserSubscription;
import com.poc.auser.main.repository.UserDetailsRepository;
import com.poc.auser.main.repository.UserSubscriptionRepository;
import com.poc.auser.master.entity.Country;
import com.poc.auser.master.entity.District;
import com.poc.auser.master.entity.IndianNewspaperLanguage;
import com.poc.auser.master.entity.Mandal;
import com.poc.auser.master.entity.State;
import com.poc.auser.master.entity.StatewiseLocation;
import com.poc.auser.master.entity.Vendor;
import com.poc.auser.master.entity.VendorId;
import com.poc.auser.master.repository.CountryRepository;
import com.poc.auser.master.repository.DistrictRepository;
import com.poc.auser.master.repository.GenerateLocationNameRepository;
import com.poc.auser.master.repository.IndianNewspaperLanguageRepository;
import com.poc.auser.master.repository.MandalRepository;
import com.poc.auser.master.repository.StateRepository;
import com.poc.auser.master.repository.StatewiseLocationRepository;
import com.poc.auser.master.repository.VendorRepository;
import com.poc.auser.request.DistrictDTO;
import com.poc.auser.request.MandalDTO;
import com.poc.auser.request.NewspaperLanguageDTO;
import com.poc.auser.request.StateDTO;
import com.poc.auser.request.StatewiseLocationDTO;
import com.poc.auser.request.UserSubscriptionDTO;

@Service
@Transactional
public class CreatePostService {

	@Autowired
    private StateRepository stateRepository;
    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;
    @Autowired
    private DistrictRepository districtRepository;
    @Autowired
    private MandalRepository mandalRepository;
    @Autowired
    private IndianNewspaperLanguageRepository newspaperLanguageRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private StatewiseLocationRepository statewiseLocationRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private GenerateLocationNameRepository generateLocationNameRepository;
    @Autowired
    private VendorRepository vendorRepository;

    public State createState(StateDTO stateDTO) {
        State state = new State();
        state.setStateName(stateDTO.getStateName());
        state.setCountryId(stateDTO.getCountryId().intValue());
        return stateRepository.save(state);
    }

    public District createDistrict(DistrictDTO districtDTO, StateDTO stateDTO) {
    	Optional<State> stateId = stateRepository.findById(stateDTO.getStateId().intValue());
    	if(stateId==null || stateId.isEmpty()) {
    		createState(stateDTO);
    	}
        District district = new District();
        district.setDistrictName(districtDTO.getDistrictName());
        district.setState(stateId.get());
        return districtRepository.save(district);
    }

    public Mandal createMandal(MandalDTO mandalDTO, DistrictDTO districtDTO, StateDTO stateDTO) {
    	Optional<State> stateId = stateRepository.findById(stateDTO.getStateId().intValue());
    	if(stateId==null || stateId.isEmpty()) {
    		createState(stateDTO);
    	}
    	Optional<District> districtId = districtRepository.findById(districtDTO.getDistrictId().intValue());
    	if(districtId==null || districtId.isEmpty()) {
    		createDistrict(districtDTO, stateDTO);
    	}
        Mandal mandal = new Mandal();
        mandal.setMandalName(mandalDTO.getMandalName());
        mandal.setDistrict(districtId.get());
        return mandalRepository.save(mandal);
    }

    public IndianNewspaperLanguage createNewspaperLanguage(NewspaperLanguageDTO newspaperLanguageDTO) {
        IndianNewspaperLanguage language = new IndianNewspaperLanguage();
        language.setLanguageName(newspaperLanguageDTO.getLanguageName());
        return newspaperLanguageRepository.save(language);
    }


    //If every thing present then only we can create this, other wise throw exception
    public StatewiseLocation createStatewiseLocation(StatewiseLocationDTO statewiseLocationDTO) {
    	Country country = countryRepository.findById(statewiseLocationDTO.getCountryId().intValue()).get();
    	State state = stateRepository.findById(statewiseLocationDTO.getStateId().intValue()).get();
    	District district = districtRepository.findById(statewiseLocationDTO.getDistrictId().intValue()).get();
    	Mandal mandal = mandalRepository.findById(statewiseLocationDTO.getMandalId()).get();
    	
    	if(country.getCountryId()==null || state.getStateId()==null ||district.getDistrictId()==null ||mandal.getMandalId()==null) {
    		throw new RuntimeException("Country : "+country.getCountryId()+"or state : "+ state.getStateId()+"or district : "+district.getDistrictId()+" or mandal : "+mandal.getMandalId()+"not present. ");
    	}else {
        StatewiseLocation location = new StatewiseLocation();
        location.setLocationName(generateLocationNameRepository.generateLocationName(state.getStateId().intValue(), district.getDistrictId().intValue(), mandal.getMandalId().intValue()));
        location.setCountry(country);
        location.setState(state);
        location.setDistrict(district);
        location.setMandal(mandal);
        return statewiseLocationRepository.save(location);
    	}
    }
    public UserDetails createUserDetails(UserDetails userDetails) {
    	
    	if(userDetails.getActive()=='N' || userDetails.getAge()<=15) {
    		throw new RuntimeException("Sorry! unable to register this user becuase of the age or inactive.");
    	}
    	userDetails.setActive('Y');
    	userDetails.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        return userDetailsRepository.save(userDetails);
    }

    @Transactional
    public UserSubscription createUserSubscription(UserSubscriptionDTO userSubscriptionDTO) {
        UserDetails userDetails = userDetailsRepository.findById(userSubscriptionDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        VendorId vendorId = new VendorId(
                userSubscriptionDTO.getNewspaperMasterId(),
                userSubscriptionDTO.getStatewiselocationId(),
                userSubscriptionDTO.getNewspaperMasterId()
        );
        Vendor vendor = vendorRepository.findById(vendorId).orElseThrow(() -> new RuntimeException("Vendor not found"));

        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUserDetails(userDetails);
        userSubscription.setVendor(vendor);
        userSubscription.setSubscriptionStartDate((Date) userSubscriptionDTO.getSubscriptionStartDate());
        userSubscription.setSubscriptionEndDate((Date) userSubscriptionDTO.getSubscriptionEndDate());

        return userSubscriptionRepository.save(userSubscription);
    }
}
