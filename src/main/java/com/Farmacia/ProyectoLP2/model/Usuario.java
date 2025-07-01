package com.Farmacia.ProyectoLP2.model;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_USUARIOS")
@Getter @Setter
@DynamicInsert
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private int idUsuario;
	
	@Column(name = "NOMBRE",nullable = false)
	private String nombre;
	
	@Column(name = "APELLIDO",nullable = false)
	private String apellido;
	
	@Column(name = "CORREO",nullable = false)
	private String correo;
	
	@Column(name = "CLAVE",nullable = false)
	private String clave;
	
	@Column(name = "DIRECCION",nullable = false)
	private String direccion;
	
	@Column(name = "TELEFONO",nullable = false)
	private String telefono;
	
	@ManyToOne
	@JoinColumn(name = "ROL", columnDefinition = "INT DEFAULT 2")
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name = "ESTADO", columnDefinition = "INT DEFAULT 1")
	private Estado estado;
	
	public String getFullUsuario() {
		return String.format("%s - %s %s", nombre, apellido);
	}
}
