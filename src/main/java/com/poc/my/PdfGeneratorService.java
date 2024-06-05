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
        document.add(new Paragraph());
    }

    private void addInvoiceDetails(Document document, Invoice invoice) {
        PdfFont latoLight = fontLight;

        Paragraph invoiceTitle = new Paragraph("INVOICE TO: \n").setFont(latoLight).setFontSize(8).setTextAlignment(TextAlignment.LEFT);
        invoiceTitle.add(invoice.getInvoiceTo()).setFont(fontMedium).setFontSize(13.5f).setBold().setTextAlignment(TextAlignment.LEFT);
        invoiceTitle.setFixedLeading(18f);
        document.add(invoiceTitle);

        Table invoiceDetailsTable = new Table(UnitValue.createPointArray(new float[]{300f, 350f}));
        invoiceDetailsTable.setBorder(Border.NO_BORDER);

        invoiceDetailsTable.addCell(createDetailParagraph("Mobile Number: ", invoice.getMobileNumber()));
        invoiceDetailsTable.addCell(createDetailParagraph("TOTAL DUE: ", invoice.getTotalDue())).setTextAlignment(TextAlignment.RIGHT);
        invoiceDetailsTable.addCell(createDetailParagraph("Email: ", invoice.getEmail()));
        invoiceDetailsTable.addCell(createDetailParagraph("Invoice No: ", invoice.getInvoiceNo())).setTextAlignment(TextAlignment.RIGHT);
        invoiceDetailsTable.addCell(createDetailParagraph("Contact Details: ", invoice.getContactDetails()));
        invoiceDetailsTable.addCell(createDetailParagraph("Date: ", invoice.getDate())).setTextAlignment(TextAlignment.RIGHT);

        document.add(invoiceDetailsTable);
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
        addRightAlignedParagraph(document, "Subtotal: " + invoice.getTotalDue(), fontMedium, 10);
        addRightAlignedParagraph(document, "Total: " + invoice.getTotalDue(), fontMedium, 10);
    }

    private void addRightAlignedParagraph(Document document, String text, PdfFont font, float fontSize) {
        document.add(new Paragraph(text)
                .setFont(font)
                .setFontSize(fontSize)
                .setTextAlignment(TextAlignment.RIGHT));
    }

    private void addPaymentDetails(Document document, Invoice invoice) {
        PaymentDetails paymentDetails = invoice.getPaymentDetails();
        if (paymentDetails != null) {
            document.add(createDetailParagraph("Payment Method: ", paymentDetails.getPaymentMethod()));
            document.add(createDetailParagraph("Bank Name: ", paymentDetails.getBankName()));
            document.add(createDetailParagraph("Bank Account: ", paymentDetails.getBankAccount()));
        }
    }
    
    private Paragraph createDetailParagraph(String label, double totalDue) {
    	return new Paragraph().add(label + totalDue)
                .setFont(fontLight)
                .setFontSize(10);
	}

	private Paragraph createDetailParagraph(String label, String value) {
        return new Paragraph().add(label + value)
                .setFont(fontLight)
                .setFontSize(10);
    }

    private void addFooterImage(Document document, String imagePath) throws IOException {
        ImageData footerImgData = ImageDataFactory.create(imagePath);
        Image footerImg = new Image(footerImgData);
        document.add(footerImg);

        document.add(new Paragraph().setFixedLeading(10));
        document.add(new Paragraph().setFixedLeading(10));
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
