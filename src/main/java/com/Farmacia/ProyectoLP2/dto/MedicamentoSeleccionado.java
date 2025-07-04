package com.Farmacia.ProyectoLP2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicamentoSeleccionado {
	
	@NotEmpty(message = "Seleccione un producto")
	private String codProducto;
	
	@NotEmpty(message = "El producto es requerido")
	private String descripcion;

	@NotNull(message = "El precio es requerido")
	@Positive(message = "El precio debe ser mayor a 0")
	private Double precio;
	
	@NotNull(message = "La cantidad es requerida")
	@Positive(message = "Mínimo 1")
	private Integer cantidad;

	public Double getSubtotal() {
		return precio * cantidad;
	}
}
