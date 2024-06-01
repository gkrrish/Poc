package com.poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.entity.NewspaperFiles;

@Repository
public interface NewspaperFilesRepository extends JpaRepository<NewspaperFiles, Long> {

}
