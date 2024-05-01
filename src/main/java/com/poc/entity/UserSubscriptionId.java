package com.poc.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class UserSubscriptionId implements Serializable {
	private static final long serialVersionUID = 3838752332485857554L;

	@Column(name = "user_id")
    private Long userId;

    @Column(name = "newspaper_id")
    private Long newspaperId;

    @Column(name = "location_id")
    private Long locationId;

}
