package com.poc.helper;

import com.poc.response.WelcomeResponse;
import com.poc.response.WelcomeBackPDFResponse;
import com.poc.response.WelcomeImageResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ResponseHelper {

    public static ResponseEntity<?> createResponse(WelcomeResponse welcomeResponse) {
        if (welcomeResponse instanceof WelcomeBackPDFResponse) {
            WelcomeBackPDFResponse pdfResponse = (WelcomeBackPDFResponse) welcomeResponse;
            return createPdfResponse(pdfResponse.getInvoice());
        } else if (welcomeResponse instanceof WelcomeImageResponse) {
            WelcomeImageResponse imageResponse = (WelcomeImageResponse) welcomeResponse;
            return createImageResponse(imageResponse.getImageData());
        } else {
            return ResponseEntity.ok(welcomeResponse);
        }
    }

    public static ResponseEntity<byte[]> createPdfResponse(byte[] pdfData) {
        String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMMYYYY"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", todayDate + "_invoice.pdf");
        headers.setContentLength(pdfData.length);

        return new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
    }

    public static ResponseEntity<byte[]> createImageResponse(byte[] imageData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);  // or MediaType.IMAGE_JPEG if it's a JPEG
        headers.setContentDispositionFormData("attachment", "welcome-banner.png");
        headers.setContentLength(imageData.length);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
