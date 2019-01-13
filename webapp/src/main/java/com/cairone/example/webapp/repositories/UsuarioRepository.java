package com.cairone.example.webapp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cairone.example.webapp.domain.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	@Query("SELECT u FROM UsuarioEntity u WHERE u.username = ?1")
	public UsuarioEntity loadUserByUsername(String username);

	@Query("SELECT u FROM UsuarioEntity u WHERE u.correoElectronico = ?1")
	public Optional<UsuarioEntity> findByEmail(String email);

	@Query("SELECT MAX(u.id) FROM UsuarioEntity u")
	long getMaxId();
}
