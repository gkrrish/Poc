package com.poc.util;

import java.io.File;
import java.util.List;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.kernel.pdf.EncryptionConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

public class PdfPasswordUtil {
	
	public static void createPasswordProtectedPdfsForUsers(String srcPath, String destDir, List<String> users, String ownerPassword, String fileName) throws IOException, java.io.IOException {
        for (String user : users) {
            String userPassword = user; // Assuming user password is the mobile number
            String destPath = destDir + File.separator + fileName+ ".pdf";
            createPasswordProtectedPdf(srcPath, destPath, userPassword, ownerPassword);
        }
    }
	
	private static void createPasswordProtectedPdf(String srcPath, String destPath, String userPassword, String ownerPassword) throws IOException, java.io.IOException {
        PdfReader reader = new PdfReader(srcPath);
        PdfWriter writer = new PdfWriter(destPath,
                new WriterProperties()
                        .setStandardEncryption(
                                userPassword.getBytes(),
                                ownerPassword.getBytes(),
                                EncryptionConstants.ALLOW_PRINTING,
                                EncryptionConstants.ENCRYPTION_AES_128
                        )
        );
        PdfDocument pdfDoc = new PdfDocument(reader, writer);
        pdfDoc.close();
    }

}
