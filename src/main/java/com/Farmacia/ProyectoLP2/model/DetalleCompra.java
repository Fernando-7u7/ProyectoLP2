package com.Farmacia.ProyectoLP2.model;

import com.Farmacia.ProyectoLP2.dto.DetalleCompraPK;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
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
	@EmbeddedId
    private DetalleCompraPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idOrden")  
    @JoinColumn(name = "ID_ORDEN")
    private OrdenCompra ordenCompra;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idMedicamento")
    @JoinColumn(name = "ID_MEDICAMENTO")
    private Medicamento medicamento;
	
	@Column(name = "Cantidad", nullable = false)
	private Integer cantidad;
	
	@Column(name = "PRECIO", nullable = false)
	private Double precio;
	
	public Double getSubTotal() {
		return precio * cantidad;
	}
}
