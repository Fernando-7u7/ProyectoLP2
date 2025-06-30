package com.Farmacia.ProyectoLP2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_DETALLE_COMPRA")
public class DetalleCompra {
	@Id
	@Column(name = "ID_ORDEN")
	private Integer idOrden;
	
    @ManyToOne
    @JoinColumn(name = "ID_ORDEN", insertable = false, updatable = false)
    private OrdenCompra ordenCompra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_MEDICAMENTO")
	private Medicamento medicamento;
	
	@Column(name = "Cantidad", nullable = false)
	private Integer cantidad;
	
	@Column(name = "PRECIO", nullable = false)
	private Double precio;
}
