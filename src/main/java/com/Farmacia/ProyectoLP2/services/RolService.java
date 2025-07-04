package com.Farmacia.ProyectoLP2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.model.Rol;
import com.Farmacia.ProyectoLP2.repositories.IRolRepository;

@Service
public class RolService {
	@Autowired
	private IRolRepository iRolRepository;
	
	public List<Rol> getAll(){
		return iRolRepository.findAll();
	}
}
