package com.Farmacia.ProyectoLP2.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoFilter {
	private Integer idCategoria;
	private Integer idProveedor;
	private String preescripcion;
}
