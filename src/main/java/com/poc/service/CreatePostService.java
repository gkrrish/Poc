package com.poc.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poc.entity.UserDetails;
import com.poc.entity.UserSubscription;
import com.poc.master.entity.Country;
import com.poc.master.entity.District;
import com.poc.master.entity.IndianNewspaperLanguage;
import com.poc.master.entity.Mandal;
import com.poc.master.entity.State;
import com.poc.master.entity.StatewiseLocation;
import com.poc.master.entity.Vendor;
import com.poc.master.entity.VendorId;
import com.poc.master.repository.CountryRepository;
import com.poc.master.repository.DistrictRepository;
import com.poc.master.repository.GenerateLocationNameRepository;
import com.poc.master.repository.IndianNewspaperLanguageRepository;
import com.poc.master.repository.MandalRepository;
import com.poc.master.repository.StateRepository;
import com.poc.master.repository.StatewiseLocationRepository;
import com.poc.master.repository.VendorRepository;
import com.poc.repository.UserDetailsRepository;
import com.poc.repository.UserSubscriptionRepository;
import com.poc.request.DistrictDTO;
import com.poc.request.MandalDTO;
import com.poc.request.NewspaperLanguageDTO;
import com.poc.request.StateDTO;
import com.poc.request.StatewiseLocationDTO;
import com.poc.request.UserSubscriptionDTO;

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
