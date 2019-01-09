package com.cairone.example.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cairone.example.webapp.domain.RolEntity;

public interface RolRepository extends JpaRepository<RolEntity, Long> {

}
