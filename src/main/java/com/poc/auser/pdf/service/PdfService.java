package com.poc.auser.pdf.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.auser.main.entity.UserDetails;
import com.poc.auser.main.repository.UserDetailsRepository;
import com.poc.auser.master.entity.Invoice;
import com.poc.auser.master.repository.InvoiceRepository;
import com.poc.auser.pdf.invoice.InvoiceResponse;
import com.poc.auser.pdf.invoice.PaymentDetails;
import com.poc.auser.pdf.invoice.PdfGeneratorService;
import com.poc.auser.response.ExistingUserDetails;

@Service
public class PdfService {

	@Autowired
	private PdfGeneratorService pdfGeneratorService;
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;
	

	public byte[] generateInvoice(ExistingUserDetails existingUserDetails) throws IOException {
		
		Optional<UserDetails> userDetailsOptional = userDetailsRepository.findByMobileNumber(existingUserDetails.getMobileNumber());
		UserDetails userDetails = userDetailsOptional.get();
		Optional<Invoice> invoiceUserDetails = invoiceRepository.findByUserOrderByInvoiceDateDesc(userDetails).stream().findFirst();
		
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
