package com.Farmacia.ProyectoLP2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "TB_CATEGORIA")
public class Categoria {
	@Id
	@Column(name="ID_CATEGORIA")
	private Integer idCategoria;
	
	@Column(name="DESCRIPCION", nullable = false)
	private String descripcion;
}
