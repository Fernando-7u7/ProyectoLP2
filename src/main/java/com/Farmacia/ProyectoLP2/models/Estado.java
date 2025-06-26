package com.Farmacia.ProyectoLP2.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "TB_ESTADO")
public class Estado {

	@Id
	@Column(name = "ID_ESTADO")
	private int idEstado;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
}
