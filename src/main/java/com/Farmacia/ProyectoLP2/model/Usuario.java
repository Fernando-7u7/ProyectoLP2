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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_USUARIOS")
@Getter @Setter
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_USUARIO")
	private int idUsuario;
	
	@Column(name = "NOMBRE",nullable = false)
	@NotEmpty(message = "El nombre es requerido")
	private String nombre;
	
	@Column(name = "APELLIDO",nullable = false)
	@NotEmpty(message = "Los apellidos son requeridos")
	private String apellido;
	
	@Column(name = "CORREO", nullable = false)
	@NotEmpty(message = "El correo es requerido")
	@Email(message = "El correo es requerido")
	private String correo;
	
	@Column(name = "DNI",nullable = false)
	@NotEmpty(message = "El DNI es requerido")
	private String dni;
	
	@Column(name = "CLAVE",nullable = false)
	@NotEmpty(message = "La clave es requerida")
	private String clave;
	
	@Column(name = "DIRECCION",nullable = false)
	@NotEmpty(message = "La direccion es requerida")
	private String direccion;
	
	@Column(name = "TELEFONO",nullable = false)
	@Pattern(regexp = "\\d{9}", message = "El teléfono debe contener solo 9 dígitos")
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
