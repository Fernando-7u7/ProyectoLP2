package com.Farmacia.ProyectoLP2.dto;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class DetalleCompraPK implements Serializable {

	@Column(name = "ID_ORDEN")
	private Integer idOrden;

	@Column(name = "ID_MEDICAMENTO", length = 5)
	private String idMedicamento;
}