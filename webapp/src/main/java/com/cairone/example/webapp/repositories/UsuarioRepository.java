package com.cairone.example.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cairone.example.webapp.domain.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	@Query("SELECT u FROM UsuarioEntity u WHERE u.username = ?1")
	public UsuarioEntity loadUserByUsername(String username);
}
