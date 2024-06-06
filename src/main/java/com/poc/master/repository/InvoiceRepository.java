package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.Invoice;
import java.util.List;
import com.poc.entity.UserDetails;


@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
	
	List<Invoice> findByUser(UserDetails userDetails);
	List<Invoice> findByUserOrderByInvoiceDateDesc(UserDetails userDetails);
}