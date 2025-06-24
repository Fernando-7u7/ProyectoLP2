package com.Farmacia.ProyectoLP2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.model.Proveedor;
import com.Farmacia.ProyectoLP2.repositories.IProveedorRepository;

@Service
public class ProveedorService {
	@Autowired
	private IProveedorRepository proveedorRepo;
	
	public List<Proveedor> getAll(){
		return proveedorRepo.findAll();
	}
}
