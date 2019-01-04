package com.cairone.example.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cairone.example.webapp.domain.ProductoEntity;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

	@Query("SELECT MAX(p.id) FROM ProductoEntity p")
	long getMaxId();
}
