package com.poc.master.entity;

import com.poc.entity.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_STATUS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WhatsAppUserStatus {

	@Id
	@Column(name = "UserID")
	private Long userId;

	@MapsId
	@OneToOne
	@JoinColumn(name = "UserID")
	private UserDetails userDetails;

	@Column(name = "ValidateWhatsAppUser", length = 1)
	private String validateWhatsAppUser;

	@Column(name = "Blocked", length = 1)
	private String blocked;

	@Column(name = "Active", length = 1)
	private String active;

	@Column(name = "NotReachable", length = 1)
	private String notReachable;
}
