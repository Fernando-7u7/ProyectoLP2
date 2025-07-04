package com.Farmacia.ProyectoLP2.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdenFechaFilter {

	private LocalDate fechaIni;
	private LocalDate fechaFin;
}
