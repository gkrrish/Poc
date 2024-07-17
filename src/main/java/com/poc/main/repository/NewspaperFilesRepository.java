package com.poc.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.main.entity.NewspaperFiles;

@Repository
public interface NewspaperFilesRepository extends JpaRepository<NewspaperFiles, Long> {

}
