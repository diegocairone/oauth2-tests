package com.cairone.example.webapp.dtos;

import javax.validation.constraints.NotNull;

public class ProductoDto {

	private Long id = null;
	@NotNull private String nombre = null;
	@NotNull private String descripcion = null;
	
	public ProductoDto() {
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
		this.nombre = nombre == null || nombre.trim().isEmpty() ? null : nombre.trim().toUpperCase();
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion == null || descripcion.trim().isEmpty() ? null : descripcion.trim().toUpperCase();
	}
	
}
