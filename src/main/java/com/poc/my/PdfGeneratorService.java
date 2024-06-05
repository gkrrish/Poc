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
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

@Service
public class PdfGeneratorService {

	public ByteArrayInputStream generatePdf(Invoice invoice) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfWriter writer = new PdfWriter(out);
		PdfDocument pdfDoc = new PdfDocument(writer);
		Document document = new Document(pdfDoc);
		float leftMargin = 12; 
		float rightMargin = 12;
		float topMargin = 20;
		float bottomMargin = 1;
		pdfDoc.setDefaultPageSize(PageSize.A4);
		document.setMargins(topMargin, rightMargin, bottomMargin, leftMargin);
		
		// Load custom fonts
        String poppinsMedium = "D:\\FontsFree-Net-Poppins-Medium.ttf"; 
        String poppinsLight = "D:\\FontsFree-Net-Poppins-Light.ttf"; 
        String latoLightFont = "D:\\Lato-Light.ttf";
        PdfFont fontMedium = PdfFontFactory.createFont(poppinsMedium, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        PdfFont fontLight = PdfFontFactory.createFont(poppinsLight, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        PdfFont latoLight = PdfFontFactory.createFont(latoLightFont, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

		// Add header image
		String headerImgPath = "D:\\invoicelogo.jpg";
		ImageData headerImgData = ImageDataFactory.create(headerImgPath);
		Image headerImg = new Image(headerImgData);
//		headerImg.setWidth(570);
//		headerImg.setHeight(90);
		document.add(headerImg);
		document.add(new Paragraph());
		
		// Add header image
		String invoiceHeadingPath = "D:\\invoiceheading.PNG";
		ImageData invoiceHeadingImgData = ImageDataFactory.create(invoiceHeadingPath);
		Image invoiceHeadingImg = new Image(invoiceHeadingImgData);
		invoiceHeadingImg.setWidth(580);
		document.add(invoiceHeadingImg);
		
		
		 
		Paragraph invoiceTitle = new Paragraph("INVOICE TO: \n").setFont(latoLight).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
		invoiceTitle.add(invoice.getInvoiceTo()).setFont(fontMedium).setFontSize(13.5f).setBold().setTextAlignment(TextAlignment.LEFT);
		invoiceTitle.setTextAlignment(TextAlignment.LEFT);
		invoiceTitle.setFixedLeading(18f);
		document.add(invoiceTitle);
		
	    Paragraph invoiceParagraphLeft=new Paragraph();
	    invoiceParagraphLeft.add("Mobile Number: " + invoice.getMobileNumber()).setFont(fontLight).setFontSize(10);
	    invoiceParagraphLeft.add("Email: " + invoice.getEmail()).setFont(fontLight).setFontSize(10);
	    invoiceParagraphLeft.add("Contact Details: " + invoice.getContactDetails()).setFont(fontLight).setFontSize(10);
	    invoiceParagraphLeft.add("Date: " + invoice.getDate()).setFont(fontLight).setFontSize(10);
		
	    Paragraph invoiceParagraphRight=new Paragraph();
	    invoiceParagraphRight.add("TOTAL DUE: " + invoice.getTotalDue()).setFont(fontLight).setFontSize(10);
	    invoiceParagraphRight.add("Invoice No: " + invoice.getInvoiceNo()).setFont(fontLight).setFontSize(10);
	    invoiceParagraphRight.add("Date: " + invoice.getDate()).setFont(fontLight).setFontSize(10);
	    
	    Table tablex = new Table(UnitValue.createPointArray(new float[]{300f, 3500f}));
	    tablex.addCell(invoiceParagraphLeft);
	    tablex.addCell(invoiceParagraphRight).setTextAlignment(TextAlignment.RIGHT);
	    tablex.setBorder(Border.NO_BORDER);
	    tablex.setBorderTop(Border.NO_BORDER);
	    tablex.setBorderBottom(Border.NO_BORDER);
	    tablex.setBorderLeft(Border.NO_BORDER);
	    tablex.setBorderRight(Border.NO_BORDER);
	    
	    document.add(tablex);
	    
	    Paragraph newspaperSubscriptionParagraph = new Paragraph("News Paper Subscriptions :").setFontSize(9).setFont(fontLight);
	    newspaperSubscriptionParagraph.setFixedLeading(10f);
	    document.add(newspaperSubscriptionParagraph);
	    
		
		/*// Add Invoice details
        document.add(new Paragraph("Mobile Number: " + invoice.getMobileNumber()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Email: " + invoice.getEmail()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Contact Details: " + invoice.getContactDetails()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Date: " + invoice.getDate()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("TOTAL DUE: " + invoice.getTotalDue()).setFont(fontLight).setFontSize(10));
        document.add(new Paragraph("Invoice No: " + invoice.getInvoiceNo()).setFont(fontLight).setFontSize(10)); */

		// Add Table
		float[] columnWidths = { 110F, 80F, 100F, 100F, 100F, 150F, 250F };
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
			table.addCell(new Cell().add(new Paragraph(subscription.getNewspaper()).setFont(fontLight).setFontSize(10f))).setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.BLACK);
			table.addCell(new Cell().add(new Paragraph(subscription.getLanguage()).setFont(fontLight).setFontSize(10f))).setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.BLACK);
			table.addCell(new Cell().add(new Paragraph(subscription.getState()).setFont(fontLight).setFontSize(10f))).setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.BLACK);
			table.addCell(new Cell().add(new Paragraph(subscription.getDistrict()).setFont(fontLight).setFontSize(10f))).setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.BLACK);
            table.addCell(new Cell().add(new Paragraph(subscription.getMandal()).setFont(fontLight).setFontSize(10f))).setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.BLACK);
            table.addCell(new Cell().add(new Paragraph(subscription.getScheduledTime()).setFont(fontLight).setFontSize(10f))).setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.BLACK);
            table.addCell(new Cell().add(new Paragraph(String.valueOf(subscription.getMonthlySubscription())).setFont(fontLight).setFontSize(10))).setTextAlignment(TextAlignment.CENTER).setFontColor(ColorConstants.BLACK);
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
