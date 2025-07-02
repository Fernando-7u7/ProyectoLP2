package com.Farmacia.ProyectoLP2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Farmacia.ProyectoLP2.model.Rol;

public interface IRolRepository extends JpaRepository<Rol, Integer>{

	List<Rol> findAllByIdRol(Integer idRol);}
