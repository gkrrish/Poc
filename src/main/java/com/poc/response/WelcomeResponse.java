package com.poc.response;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class WelcomeResponse {

    private byte[] imageData;
    private HttpHeaders headers;

    private String message;
    private List<String> languages;

    public WelcomeResponse(String message) {
        this.message = message;
        this.loadImageBytes();
    }

    private void loadImageBytes() {
        try {
            ClassPathResource resource = new ClassPathResource("images/welcome-banner.PNG");
            this.imageData = Files.readAllBytes(resource.getFile().toPath());

            this.headers = new HttpHeaders();
            this.headers.setContentType(MediaType.IMAGE_JPEG);
            this.headers.setContentLength(this.imageData.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
