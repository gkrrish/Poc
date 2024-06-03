package com.poc.my;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

@Service
public class PdfGeneratorService {

	public ByteArrayInputStream generatePdf(Invoice invoice) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter writer = new PdfWriter(out);
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document document = new Document(pdfDoc);
		pdfDoc.setDefaultPageSize(PageSize.A4);
		
		// Load custom fonts
        String poppinsMedium = "D:\\FontsFree-Net-Poppins-Medium.ttf"; 
        String poppinsLight = "D:\\FontsFree-Net-Poppins-Light.ttf"; 
        PdfFont fontMedium = PdfFontFactory.createFont(poppinsMedium, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        PdfFont fontLight = PdfFontFactory.createFont(poppinsLight, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

		// Add header image
		String headerImgPath = "D:\\invoicelogo.jpg";
		ImageData headerImgData = ImageDataFactory.create(headerImgPath);
		Image headerImg = new Image(headerImgData);
		document.add(headerImg);

		// Add Invoice details
        document.add(new Paragraph("TAX INVOICE").setBold().setFontSize(14).setFont(fontMedium));
        document.add(new Paragraph("INVOICE TO: " + invoice.getInvoiceTo()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Mobile Number: " + invoice.getMobileNumber()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Email: " + invoice.getEmail()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Contact Details: " + invoice.getContactDetails()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Date: " + invoice.getDate()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("TOTAL DUE: " + invoice.getTotalDue()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Invoice No: " + invoice.getInvoiceNo()).setFont(fontLight).setFontSize(10));

		// Add Table
		float[] columnWidths = { 100F, 80F, 100F, 100F, 50F, 150F, 250F };
		Table table = new Table(columnWidths);
        
		// Header row with black background and white text
		table.addHeaderCell(new Cell().add(new Paragraph("NEWS PAPER").setFont(fontMedium).setFontSize(8.9f).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK)).setBold();
		table.addHeaderCell(new Cell().add(new Paragraph("LANGUAGE").setFont(fontMedium).setFontSize(8.9f).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK)).setBold();
		table.addHeaderCell(new Cell().add(new Paragraph("STATE").setFont(fontMedium).setFontSize(8.9f).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK)).setBold();
        table.addHeaderCell(new Cell().add(new Paragraph("DISTRICT").setFont(fontMedium).setFontSize(8.9f).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK)).setBold();
        table.addHeaderCell(new Cell().add(new Paragraph("MANDAL").setFont(fontMedium).setFontSize(8.9f).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK)).setBold();
        table.addHeaderCell(new Cell().add(new Paragraph("SCHEDULED TIME").setFont(fontMedium).setFontSize(8.9f).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK)).setBold();
        table.addHeaderCell(new Cell().add(new Paragraph("MONTHLY SUBSCRIPTION").setFont(fontMedium).setFontSize(8.9f).setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.BLACK)).setBold();


		for (NewspaperSubscription subscription : invoice.getSubscriptions()) {
			table.addCell(new Cell().add(new Paragraph(subscription.getNewspaper()).setFont(fontLight).setFontSize(10))).setFontColor(ColorConstants.BLACK);
			table.addCell(new Cell().add(new Paragraph(subscription.getLanguage()).setFont(fontLight).setFontSize(10))).setFontColor(ColorConstants.BLACK);
			table.addCell(new Cell().add(new Paragraph(subscription.getState()).setFont(fontLight).setFontSize(10))).setFontColor(ColorConstants.BLACK);
			table.addCell(new Cell().add(new Paragraph(subscription.getDistrict()).setFont(fontLight).setFontSize(10))).setFontColor(ColorConstants.BLACK);
            table.addCell(new Cell().add(new Paragraph(subscription.getMandal()).setFont(fontLight).setFontSize(10))).setFontColor(ColorConstants.BLACK);
            table.addCell(new Cell().add(new Paragraph(subscription.getScheduledTime()).setFont(fontLight).setFontSize(10))).setFontColor(ColorConstants.BLACK);
            table.addCell(new Cell().add(new Paragraph(String.valueOf(subscription.getMonthlySubscription())).setFont(fontLight).setFontSize(10))).setFontColor(ColorConstants.BLACK);
		}
		document.add(table);
		// Add Sub-total and Total
        document.add(new Paragraph("Subtotal: " + invoice.getTotalDue()).setFont(fontMedium).setFontSize(10).setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Total: " + invoice.getTotalDue()).setFont(fontMedium).setFontSize(10).setTextAlignment(TextAlignment.RIGHT));

        // Add payment details
        document.add(new Paragraph("Payment Method: " + invoice.getPaymentDetails().getPaymentMethod()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Bank Name: " + invoice.getPaymentDetails().getBankName()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Bank Account: " + invoice.getPaymentDetails().getBankAccount()).setFont(fontLight).setFontSize(10));
        

		// Add footer image
		String footerImgPath = "D:\\invoicelogo.jpg";
		ImageData footerImgData = ImageDataFactory.create(footerImgPath);
		Image footerImg = new Image(footerImgData);
		document.add(footerImg);
		
		Paragraph emptyLine = new Paragraph();
		emptyLine.setFixedLeading(10);
		document.add(emptyLine);
		document.add(emptyLine);
		
		final String newsOnWhatsAppAddress = "News On WhatsApp:                         Registered Address: #70, Dr. Prakash Rao Nagar, Annojiguda , Hyderabad-Telangana-500088. ";
		Paragraph footerText = new Paragraph(newsOnWhatsAppAddress).setFont(fontLight).setFontSize(8f).setFontColor(ColorConstants.WHITE);
		float[] footerColumnWidth = { 600f};
		
		Table footerTable=new Table(footerColumnWidth);
		footerTable.setBackgroundColor(ColorConstants.BLACK).addCell(new Cell().add(footerText));
		document.add(footerTable);

		document.close();
		return new ByteArrayInputStream(out.toByteArray());
	}

}
