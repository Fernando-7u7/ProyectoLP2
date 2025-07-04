package com.Farmacia.ProyectoLP2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_ROLES")
@Getter
@Setter
public class Rol {
	@Id
	@Column(name = "ID_ROL")
	private int idRol;
	
	@Column(name = "DESCRIPCION")
	private String descripcion;
	
	public String getRoles() {
	    switch (descripcion) {
	        case "C":
	            return "Cliente";
	        case "F":
	            return "Farmacéutico";
	        case "A":
	            return "Administrador";
	        default:
	            return "Rol desconocido";
	    }
	}
}
