package com.poc.main.entity;

import com.poc.master.entity.MasterNewspaper;
import com.poc.master.entity.StatewiseLocation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "DAILY_BANNER")
@Data
public class DailyBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_banner_seq")
    @SequenceGenerator(name = "daily_banner_seq", sequenceName = "DAILY_BANNER_SEQ", allocationSize = 1)
    @Column(name = "banner_id")
    private Integer bannerId;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false)
    private StatewiseLocation location;

    @ManyToOne
    @JoinColumn(name = "newspaper_id", referencedColumnName = "newspaper_master_id", nullable = false)  
    private MasterNewspaper newspaper;

    @Column(name = "banner_file_location", nullable = false)
    private String bannerFilePath;
}

