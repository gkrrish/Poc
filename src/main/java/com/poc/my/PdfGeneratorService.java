package com.poc.my;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

@Service
public class PdfGeneratorService {

	public ByteArrayInputStream generatePdf(Invoice invoice) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter writer = new PdfWriter(out);
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document document = new Document(pdfDoc);

		// Add header image
		String headerImgPath = "D:\\invoicelogo.jpg";
		ImageData headerImgData = ImageDataFactory.create(headerImgPath);
		Image headerImg = new Image(headerImgData);
		document.add(headerImg);

		// Add Invoice details
		document.add(new Paragraph("TAX INVOICE").setBold().setFontSize(14));
		document.add(new Paragraph("INVOICE TO: " + invoice.getInvoiceTo()));
		document.add(new Paragraph("Mobile Number: " + invoice.getMobileNumber()));
		document.add(new Paragraph("Email: " + invoice.getEmail()));
		document.add(new Paragraph("Contact Details: " + invoice.getContactDetails()));
		document.add(new Paragraph("Date: " + invoice.getDate()));
		document.add(new Paragraph("TOTAL DUE: " + invoice.getTotalDue()));
		document.add(new Paragraph("Invoice No: " + invoice.getInvoiceNo()));

		// Add Table
		float[] columnWidths = { 60F, 60F, 60F, 60F, 60F, 60F, 60F };
		Table table = new Table(columnWidths);
		
		table.addHeaderCell(new Cell().add(new Paragraph("NEWS PAPER")));
		table.addHeaderCell(new Cell().add(new Paragraph("LANGUAGE")));
		table.addHeaderCell(new Cell().add(new Paragraph("STATE")));
		table.addHeaderCell(new Cell().add(new Paragraph("DISTRICT")));
		table.addHeaderCell(new Cell().add(new Paragraph("MANDAL")));
		table.addHeaderCell(new Cell().add(new Paragraph("SCHEDULED TIME")));
		table.addHeaderCell(new Cell().add(new Paragraph("MONTHLY SUBSCRIPTION")));

		for (NewspaperSubscription subscription : invoice.getSubscriptions()) {
			table.addCell(new Cell().add(new Paragraph(subscription.getNewspaper())));
			table.addCell(new Cell().add(new Paragraph(subscription.getLanguage())));
			table.addCell(new Cell().add(new Paragraph(subscription.getState())));
			table.addCell(new Cell().add(new Paragraph(subscription.getDistrict())));
			table.addCell(new Cell().add(new Paragraph(subscription.getMandal())));
			table.addCell(new Cell().add(new Paragraph(subscription.getScheduledTime())));
			table.addCell(new Cell().add(new Paragraph(String.valueOf(subscription.getMonthlySubscription()))));
		}
		document.add(table);

		// Add payment details
		document.add(new Paragraph("Payment Method: " + invoice.getPaymentDetails().getPaymentMethod()));
		document.add(new Paragraph("Bank Name: " + invoice.getPaymentDetails().getBankName()));
		document.add(new Paragraph("Bank Account: " + invoice.getPaymentDetails().getBankAccount()));

		// Add footer image
		String footerImgPath = "D:\\invoicelogo.jpg";
		ImageData footerImgData = ImageDataFactory.create(footerImgPath);
		Image footerImg = new Image(footerImgData);
		document.add(footerImg);

		document.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

}
