package com.cairone.example.webapp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity @Table(name="roles", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"nombre"})
})
public class RolEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id @Column(name="rol_id")
	private Long id = null;
	
	@Column(name="nombre", nullable=false, length=200)
	private String nombre = null;
	
	public RolEntity() {
	}

	public RolEntity(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
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
		RolEntity other = (RolEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RolEntity [id=" + id + ", nombre=" + nombre + "]";
	}
	
}
