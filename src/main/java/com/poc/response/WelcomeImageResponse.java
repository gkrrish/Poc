package com.poc.response;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@DefaultSerializer
@JsonSerialize
@JsonDeserialize
public class WelcomeImageResponse extends WelcomeResponse {
    private String imageBase64;

    public WelcomeImageResponse(String message) throws IOException {
        super(message);
        setImageBase64();
    }

    public void setImageBase64() throws IOException {
        ClassPathResource resource = new ClassPathResource("images/welcome-banner.PNG");
        byte[] imageData = Files.readAllBytes(resource.getFile().toPath());
        this.imageBase64 = Base64.getEncoder().encodeToString(imageData);
    }
}
