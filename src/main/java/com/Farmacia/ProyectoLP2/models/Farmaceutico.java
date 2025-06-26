package com.Farmacia.ProyectoLP2.models;


import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@DynamicInsert
@Table(name = "TB_FARMACEUTICOS")
public class Farmaceutico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_FARMACEUTICO")
	private int idFarmaceutico;
	
	@Column(name = "NOMBRES")
	@NotEmpty(message = "El nombre es requerido")
	private String nombre;
	
	@Column(name = "APELLIDOS")
	@NotEmpty(message = "Los apellidos son requeridos")
	private String apellidos;
	
	@Column(name = "TIPO_DOCUMENTO")
	@NotEmpty(message = "Seleccione un tipo de documento")
	private String tipoDoc;
	
	@Column(name = "DOCUMENTO")
	@NotEmpty(message = "El número de documento es requerido")
	private String documento;
	
	@Column(name = "TELEFONO")
	@NotEmpty(message = "El teléfono es requerido")
	private String fono;
	
	@Column(name = "CORREO")
	@NotEmpty(message = "El correo es requerido")
	private String correo;
	
	@Column(name = "DIRECCION")
	@NotEmpty(message = "La direccion es requerida")
	private String direccion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADO")
	private Estado estado;
	
}
