package com.cairone.example.webapp.oauth2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.cairone.example.webapp.domain.UsuarioEntity;

public class UserPrincipal implements OAuth2User, UserDetails {
	
	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(UserPrincipal.class);
	
	private Long id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(UsuarioEntity usuarioEntity) {
        List<GrantedAuthority> authorities = 
    		usuarioEntity.getRoles().stream()
    		.map(rol -> { 
    			if(logger.isDebugEnabled()) logger.debug("{} IS IN ROLE {}", usuarioEntity, rol);
				return new SimpleGrantedAuthority(rol.getNombre()); 
    		})
    		.collect(Collectors.toList());

        return new UserPrincipal(
        		usuarioEntity.getId(),
        		usuarioEntity.getCorreoElectronico(),
        		usuarioEntity.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(UsuarioEntity usuarioEntity, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(usuarioEntity);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
