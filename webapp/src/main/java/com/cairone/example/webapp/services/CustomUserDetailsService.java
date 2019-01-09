package com.cairone.example.webapp.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cairone.example.webapp.domain.UsuarioEntity;
import com.cairone.example.webapp.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOG = LogManager.getLogger();
	
	@Autowired private UsuarioRepository usuarioRepository = null;
	
	@Override @Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UsuarioEntity usuarioEntity = usuarioRepository.loadUserByUsername(username);
		
		if(usuarioEntity == null) {
			LOG.error("No se encuentra el usuario: {}", username);
			throw new UsernameNotFoundException("No se encuentra el usuario: " + username);
		}
		usuarioEntity.getAuthorities();
		
		return usuarioEntity;
	}
}
