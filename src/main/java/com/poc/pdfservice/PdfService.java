package com.poc.pdfservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.entity.UserDetails;
import com.poc.invoice.InvoiceResponse;
import com.poc.invoice.PaymentDetails;
import com.poc.invoice.PdfGeneratorService;
import com.poc.master.entity.Invoice;
import com.poc.master.repository.InvoiceRepository;
import com.poc.repository.UserDetailsRepository;
import com.poc.response.ExistingUserDetails;

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
