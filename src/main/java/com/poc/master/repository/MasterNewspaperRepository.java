package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.MasterNewspaper;


@Repository
public interface MasterNewspaperRepository extends JpaRepository<MasterNewspaper, Long> {

}
