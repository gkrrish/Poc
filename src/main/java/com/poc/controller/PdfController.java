package com.poc.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poc.invoice.Invoice;
import com.poc.invoice.NewspaperSubscription;
import com.poc.invoice.PaymentDetails;
import com.poc.invoice.PdfGeneratorService;

@RestController
public class PdfController {

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    @GetMapping("/generate-pdf")
    public ResponseEntity<?> generatePdf(@RequestParam("invoiceTo") String invoiceTo,
                                         @RequestParam("mobileNumber") String mobileNumber,
                                         @RequestParam("email") String email,
                                         @RequestParam("contactDetails") String contactDetails,
                                         @RequestParam("date") String date,
                                         @RequestParam("totalDue") double totalDue,
                                         @RequestParam("invoiceNo") String invoiceNo) {
        try {
            // Create dummy data for the example
            // Normally, you would retrieve this from a database or other source
            Invoice invoice = new Invoice();
            invoice.setInvoiceTo(invoiceTo);
            invoice.setMobileNumber(mobileNumber);
            invoice.setEmail(email);
            invoice.setContactDetails(contactDetails);
            invoice.setDate(date);
            invoice.setTotalDue(totalDue);
            invoice.setInvoiceNo(invoiceNo);

            // Add dummy subscription data
            List<NewspaperSubscription> subscriptions = new ArrayList<>();
            subscriptions.add(new NewspaperSubscription("Eenadu","Telugu","Telangana","Hyderabad", "Gopalpet", "04:00 AM", 80));
            subscriptions.add(new NewspaperSubscription("Eenadu","Telugu","Telangana","Hyderabad", "Gopalpet", "04:00 AM", 80));
            subscriptions.add(new NewspaperSubscription("Eenadu","Telugu","Telangana","Hyderabad", "Gopalpet", "04:00 AM", 80));
            
            invoice.setSubscriptions(subscriptions);

            // Add dummy payment details
            PaymentDetails paymentDetails = new PaymentDetails();
            paymentDetails.setPaymentMethod("Bank Transfer");
            paymentDetails.setBankName("Francisco Andrade");
            paymentDetails.setBankAccount("1234567890");
            invoice.setPaymentDetails(paymentDetails);

            // Generate PDF
            ByteArrayInputStream pdf = pdfGeneratorService.generatePdf(invoice);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=invoice.pdf");
            headers.setContentType(MediaType.APPLICATION_PDF);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdf.readAllBytes());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error generating PDF: " + e.getMessage());
        }
    }
}