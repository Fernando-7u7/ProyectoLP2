package com.Farmacia.ProyectoLP2.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Farmacia.ProyectoLP2.model.Estado;
import com.Farmacia.ProyectoLP2.model.Medicamento;

public interface IMedicamentoRepository extends JpaRepository<Medicamento, String> {
	List<Medicamento> findAllByOrderByIdMedicamentoDesc();

	@Query("""
			SELECT m FROM Medicamento m
			WHERE
			(:estado is null or m.estado = :estado)
			ORDER BY
			    m.idMedicamento DESC
			""")
	List<Medicamento> findByEstado(Estado estado);

	long count();

	@Query("SELECT COUNT(m) FROM Medicamento m WHERE m.stockActual < 10")
	long countMedicinesStockLow();

	@Query("""
			SELECT M FROM Medicamento M
			WHERE (:idCategoria IS NULL OR M.categoria.idCategoria = :idCategoria)
			         AND
			         (:idProveedor IS NULL OR M.proveedor.idProveedor = :idProveedor)
			         ORDER BY
			         M.idMedicamento DESC
			         """)
	List<Medicamento> findAllWithFilters(@Param("idCategoria") Integer idCategoria,
			@Param("idProveedor") Integer idProveedor);

	@Query("SELECT m FROM Medicamento m WHERE m.fechaVencimiento <= :fechaLimite AND m.fechaVencimiento >= CURRENT_DATE")
	List<Medicamento> findExpiredMedications(@Param("fechaLimite") LocalDate fechaLimite);

	@Modifying
	@Query("UPDATE Medicamento m SET m.estado.id = 1 WHERE m.idMedicamento = :id")
	void activarMedicamento(@Param("id") String id);

	@Modifying
	@Query("UPDATE Medicamento m SET m.estado.id = 2 WHERE m.idMedicamento = :id")
	void desactivarMedicamento(@Param("id") String id);

	@Modifying
	@Query(value = "CALL SP_CREATE_MEDICINE(:imagen, :nombre, :precio, :stockActual,:fechaVencimiento, :descripcion, :idProveedor, :preescripcion, :idCategoria)", nativeQuery = true)
	void createMedicine(@Param("imagen") byte[] imagen, @Param("nombre") String nombre, @Param("precio") Double precio,
			@Param("stockActual") Integer stockActual, @Param("fechaVencimiento") LocalDate fechaVencimiento,
			@Param("descripcion") String descripcion, @Param("idProveedor") Integer idProveedor,
			@Param("preescripcion") String preescripcion, @Param("idCategoria") Integer idCategoria);

	@Modifying
	@Query(value = "CALL SP_UPDATE_MEDICINE(:idMedicamento, :imagen, :nombre, :precio, :stockActual, :fechaVencimiento, :descripcion, :idProveedor, :preescripcion, :idCategoria)", nativeQuery = true)
	void updateMedicine(@Param("idMedicamento") String idMedicamento, @Param("imagen") byte[] imagen,
			@Param("nombre") String nombre, @Param("precio") Double precio, @Param("stockActual") Integer stockActual,
			@Param("fechaVencimiento") LocalDate fechaVencimiento, @Param("descripcion") String descripcion,
			@Param("idProveedor") Integer idProveedor, @Param("preescripcion") String preescripcion,
			@Param("idCategoria") Integer idCategoria);

}
