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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
	@Pattern(regexp = "20\\d{9}", message = "El RUC debe comenzar con '20' y tener 11 dígitos en total")
	private String ruc;
	
	@Column(name = "RAZON_SOCIAL", nullable = false)
	@NotBlank(message = "La razón social es requerida")
	private String razonSocial;

	@Column(name = "TELEFONO", nullable = false)
	@NotBlank(message = "El teléfono es requerido")
	@Pattern(regexp = "\\d{9}", message = "El teléfono debe contener solo 9 dígitos")
	private String telefono;

	@Column(name = "DIRECCION", nullable = false)
	@NotBlank(message = "La dirección es requerida")
	private String direccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADO", columnDefinition = "INT DEFAULT 1")
	private Estado estado;

}
