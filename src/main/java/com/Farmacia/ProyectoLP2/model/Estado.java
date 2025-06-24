package com.Farmacia.ProyectoLP2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "TB_ESTADO")
public class Estado {
	@Id
	@Column(name="ID_ESTADO")
	private Integer idEstado;
	
	@Column(name="DESCRIPCION", nullable = false)
	private String descripcion;
}
