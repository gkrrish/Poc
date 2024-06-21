package com.poc.auser.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.itextpdf.io.exceptions.IOException;
import com.poc.auser.main.entity.UserDetails;
import com.poc.auser.main.repository.UserDetailsRepository;
import com.poc.auser.master.entity.Country;
import com.poc.auser.master.entity.State;
import com.poc.auser.master.repository.BatchJobRepository;
import com.poc.auser.master.repository.CountryRepository;
import com.poc.auser.master.repository.DistrictRepository;
import com.poc.auser.master.repository.IndianNewspaperLanguageRepository;
import com.poc.auser.master.repository.MandalRepository;
import com.poc.auser.master.repository.StateRepository;
import com.poc.auser.pdf.service.PdfService;
import com.poc.auser.request.WelcomeRequest;
import com.poc.auser.response.AvailableNewspapersByMandalwise;
import com.poc.auser.response.ExistingUserDetails;
import com.poc.auser.response.UserDetailsResponse;
import com.poc.auser.response.WelcomeResponse;
import com.poc.auser.util.BatchTimePeriod;
import com.poc.auser.util.StringUtils;
import com.poc.zdashboard.vendor.repository.CustomMandalDashboardReportRepository;

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
	
	
	
	public ResponseEntity<?> processWelcomeRequest(WelcomeRequest request) throws java.io.IOException {
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
		List<String> allLanguageNames = languageRepository.findAllLanguageNames();
		return allLanguageNames;
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

	

    private ResponseEntity<?> generateWelcomeResponse() {
        WelcomeResponse welcomeResponse = new WelcomeResponse(StringUtils.WELCOME_MESSAGE);
        welcomeResponse.setLanguages(getAllLanguges());
        return ResponseEntity.ok(welcomeResponse);
    }

    private ResponseEntity<?> generateInvoiceResponse(ExistingUserDetails existingUserDetails) throws java.io.IOException {
    	String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMYYYY"));
        try {
            byte[] invoice = pdfService.generateInvoice(existingUserDetails);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", todayDate+"_invoice"+".pdf");
            return new ResponseEntity<>(invoice, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly, maybe return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate invoice");
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
