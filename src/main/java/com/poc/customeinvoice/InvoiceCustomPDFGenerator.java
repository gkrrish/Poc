package com.poc.customeinvoice;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.poc.entity.UserDetails;
public class InvoiceCustomPDFGenerator {
	
    public byte[] customePDF(UserDetails userDetails) throws FileNotFoundException {
    	userDetails=new UserDetails();
    	userDetails.setMobileNumber("+919876543210");
        String pdfName= userDetails.getMobileNumber()+".pdf";
        InvoiceGenerator cepdf=new InvoiceGenerator(pdfName);
        cepdf.createDocument();

        //Create Header start
        HeaderDetails header=new HeaderDetails();
        header.setInvoiceNo("RK35623").setInvoiceDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).build();
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
        List<Product> productList=cepdf.getDummyProductList();
        productList=cepdf.modifyProductList(productList);
        cepdf.createProduct(productList);
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