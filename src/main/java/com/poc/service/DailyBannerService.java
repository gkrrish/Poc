package com.poc.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poc.main.entity.DailyBanner;
import com.poc.main.repository.DailyBannerRepository;

@Service
public class DailyBannerService {

    @Autowired
    private DailyBannerRepository dailyBannerRepository;

    public Optional<DailyBanner> getBannerById(Integer id) {
        return dailyBannerRepository.findById(id);
    }

    public Optional<DailyBanner> getBannerByLocationAndNewspaper(Long locationId, Long newspaperId) {
        System.out.println("Location ID: " + locationId + ", Newspaper ID: " + newspaperId); // Add logging
        return dailyBannerRepository.findByLocationIdAndNewspaperId(locationId, newspaperId);
    }
    
    public String saveBannerImage(String bannerFilePath) throws IOException {
        Path sourcePath = Paths.get(bannerFilePath);
        String fileName = sourcePath.getFileName().toString();
        Path targetPath = Paths.get("src/main/resources/images/" + fileName);

        if (!Files.exists(targetPath)) {
            Files.copy(sourcePath, targetPath);
        }

        return "images/" + fileName;
    }
}

