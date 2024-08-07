package com.poc.master.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poc.master.entity.UserStatus;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Long> {
}