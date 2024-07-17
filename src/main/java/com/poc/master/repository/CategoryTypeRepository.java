package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.CategoryType;

@Repository
public interface CategoryTypeRepository extends JpaRepository<CategoryType, Long> {
}