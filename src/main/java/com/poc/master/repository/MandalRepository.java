package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.Mandal;

@Repository
public interface MandalRepository extends JpaRepository<Mandal, Long> {
}
