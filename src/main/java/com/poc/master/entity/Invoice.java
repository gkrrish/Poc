package com.poc.master.entity;

import java.util.Date;

import com.poc.entity.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "INVOICE")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVOICEID", nullable = false)
    private Long invoiceId;

    @ManyToOne
    @JoinColumn(name = "USERID", nullable = false)
    private UserDetails user;

    @Column(name = "INVOICEDATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;

}
