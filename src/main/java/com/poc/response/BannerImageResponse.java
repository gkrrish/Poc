package com.poc.response;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.core.io.ClassPathResource;

import com.esotericsoftware.kryo.DefaultSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@DefaultSerializer
@JsonSerialize
@JsonDeserialize
public class BannerImageResponse {
    private String imageBase64;

    public BannerImageResponse(String imagePath) throws IOException {
        setImageBase64(imagePath);
    }

    private void setImageBase64(String bannerFilePath) throws IOException {
        ClassPathResource resource = new ClassPathResource(bannerFilePath);
        if (resource.exists()) {
            byte[] imageData = Files.readAllBytes(resource.getFile().toPath());
            this.imageBase64 = Base64.getEncoder().encodeToString(imageData);
        } else {
            this.imageBase64 = null;
        }
    }
}
