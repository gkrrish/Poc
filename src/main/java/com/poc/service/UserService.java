package com.poc.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.io.exceptions.IOException;
import com.poc.main.entity.UserDetails;
import com.poc.main.repository.UserDetailsRepository;
import com.poc.master.entity.Country;
import com.poc.master.entity.State;
import com.poc.master.repository.BatchJobRepository;
import com.poc.master.repository.CountryRepository;
import com.poc.master.repository.CustomMandalDashboardReportRepository;
import com.poc.master.repository.DistrictRepository;
import com.poc.master.repository.IndianNewspaperLanguageRepository;
import com.poc.master.repository.MandalRepository;
import com.poc.master.repository.StateRepository;
import com.poc.pdf.service.PdfService;
import com.poc.request.WelcomeRequest;
import com.poc.response.AvailableNewspapersByMandalwise;
import com.poc.response.ExistingUserDetails;
import com.poc.response.UserDetailsResponse;
import com.poc.response.WelcomeBackPDFResponse;
import com.poc.response.WelcomeImageResponse;
import com.poc.response.WelcomeResponse;
import com.poc.util.BatchTimePeriod;
import com.poc.util.StringUtils;

@Service
public class UserService {

	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private IndianNewspaperLanguageRepository languageRepository;
	@Autowired
	private CountryRepository countryRepository;
	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private DistrictRepository districtRepository;
	@Autowired
	private MandalRepository mandalRepository;
	@Autowired
	private PdfService pdfService;
	@Autowired
	private BatchJobRepository batchJobRepository;
	@Autowired
	private CustomMandalDashboardReportRepository customMandalDashboardReportRepository;
	
	
	
	public WelcomeResponse processWelcomeRequest(WelcomeRequest request) throws java.io.IOException {
        ExistingUserDetails existingUserDetails = getSubscriptioinDetails(request.getMobileNumber());

        if (existingUserDetails==null ||existingUserDetails.getMobileNumber()==null|| existingUserDetails.getMobileNumber().isEmpty() || existingUserDetails.getMobileNumber().isBlank()) {
            return generateWelcomeResponse();
        } else {
            return generateInvoiceResponse(existingUserDetails);
        }
    }
	
	public ExistingUserDetails getSubscriptioinDetails(String mobileNumber) {
		Optional<UserDetails> userDetails = userDetailsRepository.findByMobileNumber(mobileNumber);
		if(userDetails==null ||userDetails.isEmpty()) {
			return new ExistingUserDetails();
		}
		List<Object[]> queryResults = userDetailsRepository.getUserDetailsByMobileNumber(mobileNumber);

        ExistingUserDetails existingUserDetails = new ExistingUserDetails();
        existingUserDetails.setMobileNumber(userDetails.get().getMobileNumber());

        List<UserDetailsResponse> detailsList = existingUserConversion(queryResults);
        existingUserDetails.setDetails(detailsList);
        double totalSubscriptionCharges = detailsList.stream().mapToDouble(details -> details.getSubscriptionCharges().doubleValue()).sum();
        existingUserDetails.setTotalSubscriptionCharges(totalSubscriptionCharges);
        
        return existingUserDetails;
	}

	public List<String> getAllLanguges() {
		return languageRepository.findAllLanguageNames();
	}

	public Optional<UserDetails> getUserDetails(Long userId) {
		return userDetailsRepository.findById(userId);
	}

	public List<String> getAllStates(String countryName) {
		Country country = countryRepository.findByCountryName(countryName);
		return stateRepository.findAllByCountryId(country.getCountryId())
                .stream()
                .map(State::getStateName)
                .collect(Collectors.toList());
	}

	

	
	public List<String> getAllDistricts(String stateName) {
		return districtRepository.findAllDistrictNamesByStateName(stateName);
	}
	public List<String> getAllMandals(String districtName) {
		return mandalRepository.findAllMandalNamesByDistrictName(districtName);
	}
	
	private List<UserDetailsResponse> existingUserConversion(List<Object[]> queryResults) {
		return queryResults.stream().map(row -> {
            UserDetailsResponse details = new UserDetailsResponse();
            details.setNewsPaperName((String) row[2]);
            details.setLanguage((String) row[3]);
            details.setState((String) row[4]);
            details.setDistrict((String) row[5]);
            details.setMandal((String) row[6]);
            details.setBatchTime((String) row[7]);
            details.setSubscriptionCharges(new BigDecimal(row[9].toString()));
            details.setSubscriptionChargesPerMonth(new BigDecimal(row[9].toString()));
            return details;
        }).collect(Collectors.toList());
	}

	

    private WelcomeImageResponse generateWelcomeResponse() throws java.io.IOException {
    	WelcomeImageResponse welcomeResponse = new WelcomeImageResponse(StringUtils.WELCOME_MESSAGE);
        welcomeResponse.setLanguages(getAllLanguges());
        return welcomeResponse;
    }

    private WelcomeBackPDFResponse generateInvoiceResponse(ExistingUserDetails existingUserDetails) throws java.io.IOException {
    	
    	WelcomeBackPDFResponse  welcomeBackPDFResponse=new WelcomeBackPDFResponse (StringUtils.WELCOME_BACK_MESSAGE);
        try {
            byte[] invoice = pdfService.generateInvoice(existingUserDetails);
            welcomeBackPDFResponse.setInvoice(invoice);
            welcomeBackPDFResponse.setDelta("Do you want Change");
            return welcomeBackPDFResponse;
        } catch (IOException e) {
            e.getMessage(); // Handle the exception properly, maybe return an error response
            welcomeBackPDFResponse.setMessage("Faild to generate the PDF File, our support team is going to work on this!");
            return welcomeBackPDFResponse;
        }
    }

	public List<String> getAllDeliveryTimes() {
		return batchJobRepository.findAllDeliveryTimes();
	}
	
	public List<String> getDeliveryTimesByTimePeriod(String timePeriod) {
        BatchTimePeriod period = BatchTimePeriod.fromString(timePeriod);
        return batchJobRepository.findDeliveryTimesInRange(period.getStartId(), period.getEndId());
    }
	
	public AvailableNewspapersByMandalwise getAvailableNewspapersByMandalwise(String stateName, String districtName) {
        return customMandalDashboardReportRepository.getAvailableNewspapersByMandalwise(stateName, districtName);
    }
}
