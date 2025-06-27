package com.Farmacia.ProyectoLP2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.Farmacia.ProyectoLP2.model.Proveedor;


public interface IProveedorRepository extends JpaRepository<Proveedor, Integer>{
	List<Proveedor> findAll();
	List<Proveedor> findByEstado_IdEstadoNot(@Param("idEstado") Integer idEstado);
	
}
