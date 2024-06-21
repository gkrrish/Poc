package com.poc.auser.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.auser.main.entity.NewspaperFiles;

@Repository
public interface NewspaperFilesRepository extends JpaRepository<NewspaperFiles, Long> {

}
