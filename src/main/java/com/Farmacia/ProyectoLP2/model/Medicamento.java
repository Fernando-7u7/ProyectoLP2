package com.Farmacia.ProyectoLP2.model;

import java.time.LocalDate;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@Table(name = "TB_MEDICAMENTOS")
public class Medicamento {
	@Id
	@Column(name = "ID_MEDICAMENTO")
	private String idMedicamento;
	
	@Lob
	@Column(name="IMAGEN")
	private byte[] imagenBytes;

	@Transient
	private MultipartFile imagen;
	
	@Transient
	private String base64Img;

	@Column(name = "NOMBRE", nullable = false)
	@NotEmpty(message = "El nombre es requerido")
	private String nombre;
	
	@Column(name = "PRECIO", nullable = false)
	@NotNull(message = "El precio es requerido")
	@Positive(message = "El precio debe ser mayor que 0")
	private Double precio;
	
	@Column(name = "STOCK_ACTUAL", nullable = false)
	@NotNull(message = "El stock actual es requerido")
	@PositiveOrZero(message = "El stock actual no puede ser negativo")
	private Integer stockActual;
	
	@Column(name="FECHA_VENCIMIENTO",nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "La fecha de vencimiento es requerida")
	@FutureOrPresent(message = "La fecha de vencimiento debe ser actual o futura")
	private LocalDate fechaVencimiento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ESTADO")
	private Estado estado;
	
	@Column(name = "DESCRIPCION")
	@NotEmpty(message = "La descripción es requerida")
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_PROVEEDOR", nullable = false)
	@NotNull(message = "Seleccione un proveedor")
	private Proveedor proveedor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_CATEGORIA", nullable = false)
	@NotNull(message = "Seleccione una categoría")
	private Categoria categoria;
	
	@Column(name="PREESCRIPCION",nullable = false)
	@NotBlank(message = "Seleccione una prescripción")
	private String preescripcion;
}
