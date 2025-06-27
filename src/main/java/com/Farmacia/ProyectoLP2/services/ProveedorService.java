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
	
	
	
	public List<Proveedor> getAll() {
		return proveedorRepo.findAll();
	}
	
    public List<Proveedor> listarProveedoresActivos() {
        return proveedorRepo.findByEstado_IdEstadoNot(2);
    }
	

	public Proveedor create(Proveedor proveedor) {
		try {
			return proveedorRepo.save(proveedor);
		} catch (Exception ex) {
			throw new RuntimeException("Error al registrar proveedor: " + ex.getMessage());
		}
	}

	public Proveedor getOne(int id) {
		return proveedorRepo.findById(id).orElseThrow();
	}

	public Proveedor update(Proveedor proveedor) {
	    try {
	        Proveedor registrado = proveedorRepo.save(proveedor);

	        String mensaje = String.format("Proveedor nro. %s actualizado", registrado.getIdProveedor());
	        return registrado;

	    } catch (Exception ex) {
	        throw new RuntimeException("Error al actualizar: " + ex.getMessage());
	    }
	}
}
