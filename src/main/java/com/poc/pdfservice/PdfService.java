package com.poc.pdfservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.poc.response.Details;
import com.poc.response.ExistingUserDetails;

@Service
public class PdfService {

	public byte[] generateInvoice(ExistingUserDetails userDetails) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		PdfWriter writer = new PdfWriter(outputStream);
		PdfDocument pdfDocument = new PdfDocument(writer);

		// Create a Document instance with desired page size
		Document document = new Document(pdfDocument, PageSize.A4);

		try {
			// Add content to PDF
			document.add(new Paragraph("Invoice for Mobile Number: " + userDetails.getMobileNumber())
					.setTextAlignment(TextAlignment.CENTER).setFontSize(12));

			List<Details> detailsList = userDetails.getDetails();
			for (Details details : detailsList) {
				document.add(new Paragraph("Newspaper Name: " + details.getNewsPaperName()));
				document.add(new Paragraph("Language: " + details.getLanguage()));
				document.add(new Paragraph("State: " + details.getState()));
				document.add(new Paragraph("District: " + details.getDistrict()));
				document.add(new Paragraph("Mandal: " + details.getMandal()));
				document.add(new Paragraph("Batch Time: " + details.getBatchTime()));
				document.add(new Paragraph("Subscription Charges: " + details.getSubscriptionCharges()));
				document.add(new Paragraph("----------------------------------------------"));
			}

			// Calculate and add total subscription charges
			double totalSubscriptionCharges = userDetails.getTotalSubscriptionCharges();
			document.add(new Paragraph("Total Subscription Charges: " + totalSubscriptionCharges)
					.setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(12));
		} finally {
			// Ensure document is properly closed
			document.close();
		}

		return outputStream.toByteArray();
	}

}
