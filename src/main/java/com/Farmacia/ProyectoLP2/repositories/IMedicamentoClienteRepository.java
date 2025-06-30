package com.Farmacia.ProyectoLP2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.Farmacia.ProyectoLP2.model.Medicamento;

public interface IMedicamentoClienteRepository extends JpaRepository<Medicamento, String> {
	@Query("""
			SELECT M FROM Medicamento M
			Where M.estado.idEstado = 1
			AND M.preescripcion = "SRM"
			""")
	List<Medicamento> findMedicinesActive();

	@Query("""
			    SELECT m
			    FROM DetalleCompra d
			    JOIN d.medicamento m
			    JOIN d.ordenCompra o
			    WHERE o.estado = 'COMPLETADA'
			    GROUP BY m.id, m.nombre
			    ORDER BY SUM(d.cantidad) DESC
			    LIMIT 9
			""")
	List<Medicamento> findMedicinesMoreSales();

	@Query("""
			    SELECT M FROM Medicamento M
			    WHERE (:idCategorias IS NULL OR M.categoria.idCategoria IN :idCategorias)
			      AND M.estado.idEstado = 1
			    ORDER BY M.idMedicamento DESC
			""")
	Page<Medicamento> findAllWithFilters(@Param("idCategorias") List<Integer> idCategorias, Pageable pageable);
}
