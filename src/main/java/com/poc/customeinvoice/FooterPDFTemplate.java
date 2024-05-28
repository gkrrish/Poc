package com.poc.customeinvoice;

import java.util.List;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import lombok.SneakyThrows;

public class FooterPDFTemplate implements IEventHandler {
    private static final float[] FULL_WIDTH = {500f};
    private final Document doc;
    private final List<String> tncList;
    private final String imagePath;

    public FooterPDFTemplate(Document doc, List<String> tncList, String imagePath) {
        this.doc = doc;
        this.tncList = tncList;
        this.imagePath = imagePath;
    }

    @SneakyThrows
    @Override
    public void handleEvent(Event currentEvent) {
        PdfDocumentEvent docEvent = (PdfDocumentEvent) currentEvent;
        Rectangle pageSize = docEvent.getPage().getPageSize();
        PdfCanvas pdfCanvas = new PdfCanvas(docEvent.getPage());

        float footerY = doc.getBottomMargin();
        System.out.println("footerY: " + footerY);

        // Create Terms and Conditions table
        Table tncTable = createTermsAndConditionsTable();
        tncTable.setFixedPosition(40f, footerY, 530f);
        this.doc.add(tncTable);

        // Add water-mark image
        addWatermarkImage(docEvent.getDocument());
    }

    private Table createTermsAndConditionsTable() {
        Table table = new Table(FULL_WIDTH);
        table.addCell(new Cell().add(new Paragraph("TERMS AND CONDITIONS\n")).setBold().setBorder(Border.NO_BORDER));
        for (String tnc : tncList) {
            table.addCell(new Cell().add(new Paragraph(tnc)).setBorder(Border.NO_BORDER));
        }
        return table;
    }

    @SneakyThrows
    private void addWatermarkImage(PdfDocument pdfDocument) {
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);
        float x = pdfDocument.getDefaultPageSize().getWidth() / 2;
        float y = pdfDocument.getDefaultPageSize().getHeight() / 2;
//        System.out.println("x = " + x + ", y = " + y);
        image.setFixedPosition(x - 120, y - 130);
        image.setOpacity(0.1f);
        doc.add(image);
    }
}
