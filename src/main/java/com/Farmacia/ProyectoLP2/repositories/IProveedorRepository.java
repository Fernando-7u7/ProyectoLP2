package com.Farmacia.ProyectoLP2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Farmacia.ProyectoLP2.model.Proveedor;


public interface IProveedorRepository extends JpaRepository<Proveedor, Integer>{
	List<Proveedor> findAll();
	List<Proveedor> findByEstado_IdEstadoNot(@Param("idEstado") Integer idEstado);

	
	@Modifying
	@Query("UPDATE Proveedor P SET P.estado.idEstado = 1 WHERE P.idProveedor = :id")
	void activarProveedor(@Param("id") Integer id);

	@Modifying
	@Query("UPDATE Proveedor P SET P.estado.idEstado = 2 WHERE P.idProveedor = :id")
	void desactivarProveedor(@Param("id") Integer id);
	
}
