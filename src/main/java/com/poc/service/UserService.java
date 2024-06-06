package com.poc.service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
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
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.poc.entity.UserDetails;
import com.poc.master.entity.Country;
import com.poc.master.entity.State;
import com.poc.master.repository.CountryRepository;
import com.poc.master.repository.DistrictRepository;
import com.poc.master.repository.IndianNewspaperLanguageRepository;
import com.poc.master.repository.MandalRepository;
import com.poc.master.repository.StateRepository;
import com.poc.pdfservice.PdfService;
import com.poc.repository.UserDetailsRepository;
import com.poc.request.WelcomeRequest;
import com.poc.response.ExistingUserDetails;
import com.poc.response.UserDetailsResponse;
import com.poc.response.WelcomeResponse;
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

	public ExistingUserDetails getSubscriptioinDetails(String mobileNumber) {
		UserDetails userDetails = userDetailsRepository.findByMobileNumber(mobileNumber);
		if(userDetails==null) {
			return new ExistingUserDetails();
		}
		List<Object[]> queryResults = userDetailsRepository.getUserDetailsByMobileNumber(mobileNumber);

        ExistingUserDetails existingUserDetails = new ExistingUserDetails();
        existingUserDetails.setMobileNumber(userDetails.getMobileNumber());

        List<UserDetailsResponse> detailsList = existingUserConversion(queryResults);

        existingUserDetails.setDetails(detailsList);

        double totalSubscriptionCharges = detailsList.stream().mapToDouble(details -> details.getSubscriptionCharges().doubleValue()).sum();
        existingUserDetails.setTotalSubscriptionCharges(totalSubscriptionCharges);
        
        return existingUserDetails;
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

	public ResponseEntity<?> processWelcomeRequest(WelcomeRequest request) throws java.io.IOException {
        ExistingUserDetails existingUserDetails = getSubscriptioinDetails(request.getMobileNumber());

        if (existingUserDetails.getMobileNumber().isEmpty() || existingUserDetails.getMobileNumber().isBlank()) {
            return generateWelcomeResponse();
        } else {
            return generateInvoiceResponse(existingUserDetails);
        }
    }

    private ResponseEntity<?> generateWelcomeResponse() {
        WelcomeResponse welcomeResponse = new WelcomeResponse(StringUtils.WELCOME_MESSAGE);
        welcomeResponse.setLanguages(getAllLanguges());
        return ResponseEntity.ok(welcomeResponse);
    }

    private ResponseEntity<?> generateInvoiceResponse(ExistingUserDetails existingUserDetails) throws java.io.IOException {
        try {
            byte[] invoice = pdfService.generateInvoice(existingUserDetails);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice.pdf");
            return new ResponseEntity<>(invoice, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception properly, maybe return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate invoice");
        }
    }
    
    public byte[] createPdf() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        PdfWriter writer = new PdfWriter(baos);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);

        // Adding Title
        Paragraph title = new Paragraph("ABOUT VEHICLE INSURED DECLARED VALUE (IDV)")
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontSize(16)
                .setBackgroundColor(ColorConstants.LIGHT_GRAY);
        document.add(title);

        // Adding Subtitle
        Paragraph subtitle = new Paragraph("Your Vehicle IDV")
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(14);
        document.add(subtitle);

        // Creating Table
        float[] columnWidths = {1, 1, 1, 1, 1};
        Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();

        // Adding Header
        table.addHeaderCell(new Cell().add(new Paragraph("Vehicle")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Non Electrical Accessory")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Electrical Accessory")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Side Car")).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addHeaderCell(new Cell().add(new Paragraph("Total IDV")).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        // Adding Data Row
        table.addCell(new Cell().add(new Paragraph("27769.77")));
        table.addCell(new Cell().add(new Paragraph("0.00")));
        table.addCell(new Cell().add(new Paragraph("0.00")));
        table.addCell(new Cell().add(new Paragraph("0.00")));
        table.addCell(new Cell().add(new Paragraph("27769.77")));

        document.add(table);
        document.close();

        return baos.toByteArray();
    }


}
