package com.poc.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poc.master.entity.Vendor;
import com.poc.master.entity.VendorDetails;
import com.poc.master.entity.VendorId;
import com.poc.master.repository.IndianNewspaperLanguageRepository;
import com.poc.master.repository.MasterNewspaperRepository;
import com.poc.master.repository.StatewiseLocationRepository;
import com.poc.master.repository.SubscriptionTypeRepository;
import com.poc.master.repository.VendorDetailsRepository;
import com.poc.master.repository.VendorRepository;
import com.poc.user.request.CreateVendorDetailsRequest;
import com.poc.user.request.CreateVendorRequest;

@Service
@Transactional
public class VendorService {

	@Autowired
    private VendorDetailsRepository vendorDetailsRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private StatewiseLocationRepository statewiseLocationRepository;

    @Autowired
    private MasterNewspaperRepository masterNewspaperRepository;

    @Autowired
    private IndianNewspaperLanguageRepository indianNewspaperLanguageRepository;

    @Autowired
    private SubscriptionTypeRepository subscriptionTypeRepository;

    public VendorDetails createVendorDetails(CreateVendorDetailsRequest request) {
        VendorDetails vendorDetails = new VendorDetails();
        vendorDetails.setVendorName(request.getVendorName());
        vendorDetails.setVendorContactDetails(request.getVendorContactDetails());
        vendorDetails.setVendorStatus(request.getVendorStatus());

        return vendorDetailsRepository.save(vendorDetails);
    }

    public Vendor createVendor(CreateVendorRequest request) {
        VendorId vendorId = new VendorId(request.getNewspaperId(), request.getLocationId(), request.getNewspaperMasterId());
        
        Vendor vendor = new Vendor();
        vendor.setId(vendorId);
        vendor.setPublicationType(request.getPublicationType());
        vendor.setCategoryId(request.getCategoryId());

        // Fetching and setting the foreign keys
        vendor.setLocation(statewiseLocationRepository.findById(request.getLocationId()).orElseThrow(() -> new RuntimeException("Location not found")));
        vendor.setNewspaperMaster(masterNewspaperRepository.findById(request.getNewspaperMasterId()).orElseThrow(() -> new RuntimeException("Newspaper master not found")));
        vendor.setNewspaperLanguage(indianNewspaperLanguageRepository.findById(request.getNewspaperLanguageId().intValue()).orElseThrow(() -> new RuntimeException("Newspaper language not found")));
        vendor.setSubscriptionType(subscriptionTypeRepository.findById(request.getSubscriptionTypeId()).orElseThrow(() -> new RuntimeException("Subscription type not found")));

        return vendorRepository.save(vendor);
    }

}
