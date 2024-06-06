package com.poc.my;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;

@Service
public class PdfGeneratorService {

    private PdfFont fontMedium;
    private PdfFont fontLight;
    String EMPTY_SPACE="                                         ";
    final private String newsOnWhatsAppAddress = "News On WhatsApp:"+EMPTY_SPACE+"Registered Address: #70, Dr. Prakash Rao Nagar, Annojiguda , Hyderabad-Telangana-500088. ";

    public ByteArrayInputStream generatePdf(Invoice invoice) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(out);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        
        setupDocument(document, pdfDoc);
        loadFonts();
        
        addHeaderImage(document, "D:\\invoicelogo.jpg");
        addHeaderImage(document, "D:\\invoiceheading.PNG");
        
        addInvoiceDetails(document, invoice);
        
        addSubscriptionTable(document, invoice);
        
        addInvoiceSummary(document, invoice);
        
        addPaymentDetails(document, invoice);
        
        addFooterImage(document, "D:\\invoicelogo.jpg");
        
        addThanksAndSignature(document, invoice);
        
        addFooterText(document);
        
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }


	private void setupDocument(Document document, PdfDocument pdfDoc) {
        float leftMargin = 12;
        float rightMargin = 12;
        float topMargin = 20;
        float bottomMargin = 1;
        pdfDoc.setDefaultPageSize(PageSize.A4);
        document.setMargins(topMargin, rightMargin, bottomMargin, leftMargin);
    }

    private void loadFonts() throws IOException {
        String poppinsMedium = "D:\\FontsFree-Net-Poppins-Medium.ttf";
        String poppinsLight = "D:\\FontsFree-Net-Poppins-Light.ttf";
        fontMedium = PdfFontFactory.createFont(poppinsMedium, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
        fontLight = PdfFontFactory.createFont(poppinsLight, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
    }

    private void addHeaderImage(Document document, String imagePath) throws IOException {
        ImageData imgData = ImageDataFactory.create(imagePath);
        Image img = new Image(imgData);
        document.add(img);
    }

    private void addInvoiceDetails(Document document, Invoice invoice) {
        // Invoice title and recipient name
        Paragraph invoiceTitle = new Paragraph("INVOICE TO: \n")
                .setFont(fontLight)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.LEFT);
        invoiceTitle.setFixedLeading(13f);
        invoiceTitle.add(new Paragraph(invoice.getInvoiceTo())
                .setFont(fontMedium)
                .setFontSize(12.5f)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT));
        invoiceTitle.setFixedLeading(14f);  // Adjusted fixed leading
        document.add(invoiceTitle);

        // Left details
        Paragraph leftDetails = new Paragraph();
        leftDetails.add(new Paragraph("Mobile number   : " + invoice.getMobileNumber()).setFont(fontLight).setFontSize(10)).setFixedLeading(12f);
        leftDetails.add(new Paragraph());
        leftDetails.add(new Paragraph("Email           : " + invoice.getEmail()).setFont(fontLight).setFontSize(10)).setFixedLeading(12f);
        leftDetails.add(new Paragraph("Contact Details : " + padRight(invoice.getContactDetails(), 30)).setFont(fontLight).setFontSize(10)).setFixedLeading(12f);

        // Right details
        Paragraph rightDetails = new Paragraph();
        rightDetails.add(new Paragraph("TOTAL DUE   \n\n")
                .setFont(fontMedium)
                .setFontSize(8.9f)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT))
                .setBorder(Border.NO_BORDER);
        rightDetails.add(new Paragraph());
        rightDetails.add(new Paragraph(String.valueOf(invoice.getTotalDue()))
                .setFont(fontMedium)
                .setFontSize(13.5f)
                .setBold()
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(-10));  // Ensure the amount comes below the "TOTAL DUE" text
        rightDetails.add(new Paragraph("Invoice No: " + padRight(invoice.getInvoiceNo(), 20))
                .setFont(fontLight)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)).setFixedLeading(12f);  // Ensure minimum 20 characters
        rightDetails.add(new Paragraph("Date: " + padRight(invoice.getDate(), 20))
                .setFont(fontLight)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)).setFixedLeading(12f);  // Ensure minimum 20 characters

        // Combine left and right details in a single row
        Table combinedTable = new Table(new float[]{1, 1});
        combinedTable.setWidth(UnitValue.createPercentValue(100));
        combinedTable.setBorder(Border.NO_BORDER);

        combinedTable.addCell(new Cell().add(leftDetails).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.TOP));
        combinedTable.addCell(new Cell().add(rightDetails)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.RIGHT)
                .setVerticalAlignment(VerticalAlignment.TOP)
                .setPaddingRight(20));  // Add 30px space from the right border

        document.add(combinedTable);
    }

    private String padRight(String text, int length) {
        return String.format("%-" + length + "s", text);
    }


   

    private void addSubscriptionTable(Document document, Invoice invoice) {
        Paragraph newspaperSubscriptionParagraph = new Paragraph("News Paper Subscriptions :")
                .setFontSize(9)
                .setFont(fontLight)
                .setFixedLeading(10f);
        document.add(newspaperSubscriptionParagraph);

        float[] columnWidths = {110F, 80F, 100F, 100F, 100F, 150F, 250F};
        Table table = new Table(columnWidths);

        List<String> headers = List.of("NEWS PAPER", "LANGUAGE", "STATE", "DISTRICT", "MANDAL", "SCHEDULED TIME", "MONTHLY SUBSCRIPTION");
        headers.stream()
                .map(this::createHeaderCell)
                .forEach(table::addHeaderCell);

        invoice.getSubscriptions().forEach(subscription -> {
            Stream.of(
                    subscription.getNewspaper(),
                    subscription.getLanguage(),
                    subscription.getState(),
                    subscription.getDistrict(),
                    subscription.getMandal(),
                    subscription.getScheduledTime(),
                    String.valueOf(subscription.getMonthlySubscription())
            ).map(this::createDataCell)
                    .forEach(table::addCell);
        });

        document.add(table);
    }

    private void addInvoiceSummary(Document document, Invoice invoice) {
        // Table for the summary and payment details
        float[] columnWidths = {4, 2}; // Two equal columns
        Table table = new Table(UnitValue.createPercentArray(columnWidths));
        table.setWidth(UnitValue.createPercentValue(30)); // Adjust table width as needed
        table.setTextAlignment(TextAlignment.CENTER);
        table.setHorizontalAlignment(HorizontalAlignment.RIGHT);
//        table.setMarginTop(10); // Adjust margin as needed

        // Subtotal row
        table.addCell(createSummaryCell("Subtotal:"));
        table.addCell(createSummaryValueCell(String.valueOf(invoice.getTotalDue())));

        // Tax row
        table.addCell(createSummaryCell("Tax:"));
        table.addCell(createSummaryValueCell(String.valueOf(invoice.getTotalDue())));

        // Total row
        table.addCell(createSummaryCell("Total:"));
        table.addCell(createSummaryValueCell(String.valueOf(invoice.getTotalDue())));

        // Add the table to the document
        document.add(table);
    }

    private Cell createSummaryCell(String text) {
        return new Cell()
                .add(new Paragraph(text)
                        .setFont(fontMedium)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER))
                .setBorder(new SolidBorder(0));
    }

    private Cell createSummaryValueCell(String text) {
        return new Cell()
                .add(new Paragraph(text)
                        .setFont(fontMedium)
                        .setFontSize(10)
                        .setTextAlignment(TextAlignment.CENTER))
                .setBorder(new SolidBorder(0));
    }

    private void addPaymentDetails(Document document, Invoice invoice) {
        PaymentDetails paymentDetails = invoice.getPaymentDetails();
        if (paymentDetails != null) {
            // Payment Method
            Paragraph paymentMethodParagraph = new Paragraph("Payment Method: ")
                    .setFont(fontMedium)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            paymentMethodParagraph.setFixedLeading(10);
            document.add(paymentMethodParagraph);

            // Bank Name and Bank Account in a table
            Table table = new Table(UnitValue.createPercentArray(new float[]{50, 50}));
            table.setWidth(UnitValue.createPercentValue(100));
            table.setBorder(Border.NO_BORDER);

            // Bank Name Cell
            Paragraph bankNameParagraph = new Paragraph("Bank Name: " + paymentDetails.getBankName())
                    .setFont(fontLight)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT);
            Cell bankNameCell = new Cell().add(bankNameParagraph);
            bankNameCell.setBorder(Border.NO_BORDER);
            table.addCell(bankNameCell);

            // Bank Account Cell
            Paragraph bankAccountParagraph = new Paragraph("Bank Account: " + paymentDetails.getBankAccount())
                    .setFont(fontLight)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT);
            Cell bankAccountCell = new Cell().add(bankAccountParagraph);
            bankAccountCell.setBorder(Border.NO_BORDER);
            table.addCell(bankAccountCell);

            document.add(table);
        }
    }
    

    private void addFooterImage(Document document, String imagePath) throws IOException {
        ImageData footerImgData = ImageDataFactory.create(imagePath);
        Image footerImg = new Image(footerImgData);
        document.add(footerImg);

        document.add(new Paragraph().setFixedLeading(10));
    }
    
	private void addThanksAndSignature(Document document, Invoice invoice) {
		// Create the table with two columns
        Table table = new Table(UnitValue.createPercentArray(new float[]{70, 30}));
        table.setWidth(UnitValue.createPercentValue(100));
        table.setBorder(Border.NO_BORDER);

        // Add the "Thank you for purchase!" cell
        Paragraph thankYouParagraph = new Paragraph("Thank you for purchase!")
                .setFont(fontMedium)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.LEFT);
        thankYouParagraph.setFixedLeading(8);
        Cell thankYouCell = new Cell().add(thankYouParagraph);
        thankYouCell.setBorder(Border.NO_BORDER);
        table.addCell(thankYouCell);

        // Add the signature cell
        Paragraph signatureParagraph = new Paragraph("Krishna G")
                .setFont(fontMedium)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.RIGHT);
        signatureParagraph.setFixedLeading(8);
        Paragraph separatorParagraph = new Paragraph("___________")
                .setFont(fontLight)
                .setFontSize(10)
                .setFontColor(ColorConstants.GRAY)
                .setTextAlignment(TextAlignment.RIGHT);
        separatorParagraph.setFixedLeading(8);
        Paragraph roleParagraph = new Paragraph("Administrator")
                .setFont(fontLight)
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT);
        roleParagraph.setFixedLeading(10);
        
        Cell signatureCell = new Cell().add(signatureParagraph).add(separatorParagraph).add(roleParagraph);
        signatureCell.setBorder(Border.NO_BORDER);
        signatureCell.setTextAlignment(TextAlignment.RIGHT);
        table.addCell(signatureCell);

        // Add the table to the document
        document.add(table);

	}

    private void addFooterText(Document document) {
        Paragraph footerText = new Paragraph(newsOnWhatsAppAddress)
                .setFont(fontLight)
                .setFontSize(8f)
                .setFontColor(ColorConstants.WHITE);
        float[] footerColumnWidth = {600f};

        Table footerTable = new Table(footerColumnWidth);
        footerTable.setBackgroundColor(ColorConstants.BLACK)
                .addCell(new Cell().add(footerText).setBorder(Border.NO_BORDER));
        document.add(footerTable);
    }

    private Cell createHeaderCell(String text) {
        return new Cell()
                .add(new Paragraph(text).setFont(fontMedium).setFontSize(8.9f).setFontColor(ColorConstants.WHITE))
                .setBackgroundColor(ColorConstants.BLACK)
                .setBold();
    }

    private Cell createDataCell(String text) {
        return new Cell()
                .add(new Paragraph(text).setFont(fontLight).setFontSize(10f))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.BLACK);
    }
}
