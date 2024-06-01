package com.poc.entity;

import java.io.Serializable;

import com.poc.master.entity.Vendor;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Embeddable
@Data
public class UserSubscriptionId implements Serializable {
	private static final long serialVersionUID = 3838752332485857554L;

	@ManyToOne
    @JoinColumn(name = "user_id")
    private UserDetails user;

	@ManyToOne
    @JoinColumn(name = "newspaper_id")
    private Vendor newspaper;
}
