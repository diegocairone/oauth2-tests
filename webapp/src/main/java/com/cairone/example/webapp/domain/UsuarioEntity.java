package com.cairone.example.webapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cairone.example.webapp.oauth2.AuthProvider;

@Entity @Table(name="usuarios", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"username"})
	})
public class UsuarioEntity {

	@Id @Column(name="usuario_id")
	private Long id = null;
	
	@Column(name="nombre", length=200, nullable=false)
	private String nombre = null;
	
	@Column(name="username", length=100, nullable=false, unique=true)
	private String username = null;
	
	@Column(name="password", length=200, nullable=false)
	private String password = null;
	
	@Column(name="correo_electronico", length=200, nullable=false, unique=true)
	private String correoElectronico = null;
	
	@Column(name="account_non_expired", nullable=false)
	private boolean accountNonExpired = true;
	
	@Column(name="is_account_non_locked", nullable=false)
	private boolean isAccountNonLocked = true;
	
	@Column(name="is_credentials_non_expired", nullable=false)
	private boolean isCredentialsNonExpired = true;
	
	@Column(name="is_enabled", nullable=false)
	private boolean isEnabled = true;
	
	@Column(name="provider", nullable=true) @Enumerated(EnumType.STRING)
    private AuthProvider provider;

	@Column(name="provider_id", nullable=true)
    private String providerId;
	
	@ManyToMany(fetch = FetchType.EAGER) @JoinTable(name = "usuarios_roles",
			joinColumns = @JoinColumn(name = "usuario_id"),
			inverseJoinColumns = @JoinColumn(name = "rol_id"))
	private Set<RolEntity> roles = new HashSet<>();

	public UsuarioEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	public void setAccountNonLocked(Boolean isAccountNonLocked) {
		this.isAccountNonLocked = isAccountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	public void setIsCredentialsNonExpired(Boolean isCredentialsNonExpired) {
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public Set<RolEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolEntity> roles) {
		this.roles = roles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioEntity other = (UsuarioEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UsuarioEntity [id=" + id + ", username=" + username + "]";
	}
}
