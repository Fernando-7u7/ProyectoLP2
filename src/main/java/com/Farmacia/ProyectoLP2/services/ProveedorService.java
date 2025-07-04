package com.Farmacia.ProyectoLP2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.Proveedor;
import com.Farmacia.ProyectoLP2.repositories.IProveedorRepository;

import jakarta.transaction.Transactional;

@Service
public class ProveedorService {
	@Autowired
	private IProveedorRepository proveedorRepo;	
	
	@Transactional
	public List<Proveedor> getAll() {
		return proveedorRepo.findAllByOrderByIdProveedorDesc();
	}
	
    public List<Proveedor> listarProveedoresActivos() {
        return proveedorRepo.findByEstado_IdEstadoNot(2);
    }
	
    public long countProveedores() {
		return proveedorRepo.count();
	}
    
    @Transactional
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

	@Transactional
	public Proveedor update(Proveedor proveedor) {
	    try {
	        Optional<Proveedor> optional = proveedorRepo.findById(proveedor.getIdProveedor());

	        if (optional.isPresent()) {
	            Proveedor registrado = optional.get();

	            registrado.setRuc(proveedor.getRuc());
	            registrado.setRazonSocial(proveedor.getRazonSocial());
	            registrado.setTelefono(proveedor.getTelefono());
	            registrado.setDireccion(proveedor.getDireccion());

	            return proveedorRepo.save(registrado);
	        } else {
	            throw new RuntimeException("Proveedor no encontrado con ID: " + proveedor.getIdProveedor());
	        }

	    } catch (Exception ex) {
	        throw new RuntimeException("Error al actualizar: " + ex.getMessage());
	    }
	}

	
	@Transactional
	public ResultadoResponse delete(Integer id) {
		try {
			Proveedor registro = proveedorRepo.findById(id).orElseThrow();
			if (registro == null) {
				return new ResultadoResponse(false, "Proveedor no encontrado");
			}

			if (registro.getEstado().getIdEstado() == 1) {
				proveedorRepo.desactivarProveedor(id);
				return new ResultadoResponse(true, "Proveedor desactivado");
			} else {
				proveedorRepo.activarProveedor(id);
				return new ResultadoResponse(true, "Proveedor activado");
			}
		} catch (Exception e) {
			return new ResultadoResponse(false, "Error al cambiar estado: " + e.getMessage());
		}
	}
}