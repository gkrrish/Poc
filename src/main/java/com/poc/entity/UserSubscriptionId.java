package com.poc.entity;

import java.io.Serializable;

import com.poc.master.entity.Vendor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
public class UserSubscriptionId implements Serializable {
    private static final long serialVersionUID = 3838752332485857554L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails user;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "newspaper_id", referencedColumnName = "newspaper_id"),
        @JoinColumn(name = "location_id", referencedColumnName = "location_id"),
        @JoinColumn(name = "newspaper_master_id", referencedColumnName = "newspaper_master_id")
    })
    private Vendor vendor;
}
