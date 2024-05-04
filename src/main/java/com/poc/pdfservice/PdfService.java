package com.poc.pdfservice;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.poc.response.Details;
import com.poc.response.ExistingUserDetails;
import com.poc.util.PDFReportUtils;

@Service
public class PdfService {

	public byte[] generateInvoice(ExistingUserDetails userDetails) throws IOException {
		PDFReportUtils report = PDFReportUtils.getInstance();
		report.setPageSize(PageSize.A4.rotate());
		
		report.addParagraph(new Paragraph("News paper Subscription Details "+userDetails.getMobileNumber())
                .setFontSize(12f)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA))
        );
		
		report.addNewLine();
        report.openTable(7);
        report.addTableHeader("NEWSPAPER", "LANGUAGE", "STATE", "DISTRICT", "MANDAL", "BATCH TIME","CHARGES");
        
        List<Details> detailsList = userDetails.getDetails();
        for (Details details : detailsList) {
        	report.addTableColumn(details.getNewsPaperName());
        	report.addTableColumn(details.getLanguage());
        	report.addTableColumn(details.getState());
        	report.addTableColumn(details.getDistrict());
        	report.addTableColumn(details.getMandal());
        	report.addTableColumn(details.getBatchTime());
        	report.addTableColumn(details.getSubscriptionCharges());
        }
        double totalSubscriptionCharges = userDetails.getTotalSubscriptionCharges();
        report.addParagraph(new Paragraph("Total Subscription Charges :"+totalSubscriptionCharges)
        						.setTextAlignment(TextAlignment.RIGHT).setBold().setFontSize(12f));
        
        report.closeTable();
        report.closeDocument();
        return report.baos.toByteArray();
	}

}
