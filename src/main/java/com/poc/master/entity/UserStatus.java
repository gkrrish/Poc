package com.poc.master.entity;

import com.poc.entity.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "USER_STATUS")
@Data
public class UserStatus {

    @Id
    @Column(name = "UserID")
    private Long userId;

    @Column(name = "ValidateWhatsAppUser", length = 1)
    private String validateWhatsAppUser;

    @Column(name = "Blocked", length = 1)
    private String blocked;

    @Column(name = "Active", length = 1)
    private String active;

    @Column(name = "NotReachable", length = 1)
    private String notReachable;

    @OneToOne
    @JoinColumn(name = "UserID", referencedColumnName = "UserID")
    private UserDetails userDetails;

}
