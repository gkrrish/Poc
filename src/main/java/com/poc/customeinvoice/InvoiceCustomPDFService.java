package com.poc.customeinvoice;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.poc.response.ExistingUserDetails;
import com.poc.response.UserDetailsResponse;
public class InvoiceCustomPDFService {
	
    public byte[] customePDF(ExistingUserDetails userDetails) throws FileNotFoundException {
    	userDetails.getMobileNumber();
        String pdfName= userDetails.getMobileNumber()+".pdf";
        InvoiceGenerator cepdf=new InvoiceGenerator(pdfName);
        cepdf.createDocument();

        //Create Header start
        HeaderDetails header=new HeaderDetails();
        header.setInvoiceNo("NOW0001").setInvoiceDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))).build();
        cepdf.createHeader(header);
        //Header End

        //Create Address start
        AddressDetails addressDetails=new AddressDetails();
        addressDetails
                .setBillingCompany("News on whatsApp ")
                .setBillingName("Krishna::Hyderabad")
                .setBillingAddress("Hyderabad-5000088")
                .setBillingEmail("now@newsonwhatsapp.com")
                .setShippingName("Krishna :: User")
                .setShippingAddress("Hyderabad::Telangana::India")
                .build();

        cepdf.createAddress(addressDetails);
        //Address end

        //Product Start
        ProductTableHeader productTableHeader=new ProductTableHeader();
        cepdf.createTableHeader(productTableHeader);
        List<UserDetailsResponse> userDetailsList=userDetails.getDetails();
        List<UserDetailsResponse> modifyUserDetailsList = cepdf.modifyUserDetailsList(userDetailsList);
        cepdf.createUserDetailsResponse(modifyUserDetailsList);
        //Product End

        //Term and Condition Start
        List<String> TermsAndConditionsList=new ArrayList<>();
        TermsAndConditionsList.add(":: Get the News paper daily on whatsApp ::");
        String imagePath="src/main/resources/images/nowlogo.png";
        cepdf.createTermsAndConditions(TermsAndConditionsList,false,imagePath);
        // Term and condition end
        
        return cepdf.getPdfBytes();
    }
}