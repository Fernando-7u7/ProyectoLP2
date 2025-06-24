package com.Farmacia.ProyectoLP2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table(name = "TB_PROVEEDORES")
public class Proveedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID_PROVEEDOR")
	private Integer idProveedor;
	
	@Column(name="RUC", nullable = false)
	private String ruc;
	
	@Column(name = "RAZON_SOCIAL", nullable = false)
	private String razonSocial;

	@Column(name = "TELEFONO", nullable = false)
	private String telefono;

	@Column(name = "DIRECCION", nullable = false)
	private String direccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADO", columnDefinition = "INT DEFAULT 1")
	private Estado estado;

}
