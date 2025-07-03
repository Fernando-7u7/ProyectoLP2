package com.Farmacia.ProyectoLP2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Farmacia.ProyectoLP2.model.Estado;

public interface IEstadoRepository extends JpaRepository<Estado, Integer>{

	List<Estado> findByIdEstado(Integer idEstado);
}
