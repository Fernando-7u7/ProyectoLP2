package com.Farmacia.ProyectoLP2.model;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TB_ORDENES_COMPRA")
public class OrdenCompra {
	@Id
	@Column(name = "ID_ORDEN")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idOrden;

	@Column(name = "FECHA", nullable = false)
	private LocalDate fecha;
	
	@Column(name = "ID_USUARIO", nullable = false)
	//Luego se crea la entidad
	private Integer idUsuario;
	
	@Column(name = "ESTADO", nullable = false)
	private String estado;
	
	// En la clase OrdenCompra
	@OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL)
	// mappedBy = indica que esta relación está controlada por el atributo 'ordenCompra' de DetalleCompra.
	// cascade = CascadeType.ALL significa que operaciones como persistir o eliminar en OrdenCompra se propaguen a sus detalles.
	private List<DetalleCompra> detalles;

	public Double getTotal() {
	    if (detalles == null) return 0.0;
	    return detalles.stream()
	        .mapToDouble(detalle -> {
	            Double subTotal = detalle.getSubTotal(); 
	            return subTotal != null ? subTotal : 0.0;
	        })
	        .sum();
	}


}
