package com.poc.pdf.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.exceptions.InvoiceUserDetailsNotFoundException;
import com.poc.exceptions.UserDetailsNotFoundException;
import com.poc.main.entity.UserDetails;
import com.poc.main.repository.UserDetailsRepository;
import com.poc.master.entity.Invoice;
import com.poc.master.repository.InvoiceRepository;
import com.poc.pdf.invoice.InvoiceResponse;
import com.poc.pdf.invoice.PaymentDetails;
import com.poc.pdf.invoice.PdfGeneratorService;
import com.poc.response.ExistingUserDetails;

@Service
public class PdfService {
	
	private static final Logger logger = LoggerFactory.getLogger(PdfService.class);

	@Autowired
	private PdfGeneratorService pdfGeneratorService;
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	

	public byte[] generateInvoice(ExistingUserDetails existingUserDetails) throws IOException {
		
		Optional<UserDetails> userDetailsOptional = userDetailsRepository.findByMobileNumber(existingUserDetails.getMobileNumber());
		if (userDetailsOptional.isEmpty()) {
			logger.error("The User Details not found for mobile number : {} ", existingUserDetails.getMobileNumber());
            throw new UserDetailsNotFoundException("User details not found for mobile number: " + existingUserDetails.getMobileNumber());
        }
		UserDetails userDetails = userDetailsOptional.get();
		Optional<Invoice> invoiceUserDetails = invoiceRepository.findByUserOrderByInvoiceDateDesc(userDetails).stream().findFirst();
		
		if (invoiceUserDetails.isEmpty()) {
			logger.error("No invoice Details found for user: {} " , userDetails.getUsername());
			throw new InvoiceUserDetailsNotFoundException("No invoice details found for user: " + userDetails.getUsername());
		}
		
		 InvoiceResponse invoice = new InvoiceResponse();
         invoice.setInvoiceTo(userDetails.getUsername());
         invoice.setMobileNumber(userDetails.getMobileNumber());
         invoice.setEmail(userDetails.getUsername()+"@gmail.com");
         invoice.setContactDetails(userDetails.getLocation());
         invoice.setDate(invoiceUserDetails.get().getInvoiceDate());
         invoice.setTotalDue(existingUserDetails.getTotalSubscriptionCharges());
         invoice.setInvoiceNo(invoiceUserDetails.get().getInvoiceId());
		 
         PaymentDetails paymentDetails = new PaymentDetails();
         paymentDetails.setPaymentMethod("Credit Card");
         paymentDetails.setBankName("HDFC Bank, IFSC : HDFC0000808");
         paymentDetails.setBankAccount("CARD ENDS : XXXX XXXX XXXX 6538");
         invoice.setPaymentDetails(paymentDetails);
         
         invoice.setSubscriptions(existingUserDetails.getDetails());
         
		ByteArrayInputStream pdf = pdfGeneratorService.generatePdf(invoice);

		return pdf.readAllBytes();
	}
}
