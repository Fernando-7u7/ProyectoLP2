package com.Farmacia.ProyectoLP2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.FarmaceuticoFilter;
import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.Farmaceutico;
import com.Farmacia.ProyectoLP2.repositories.IFarmaceuticoRepository;

import jakarta.transaction.Transactional;

@Service
public class FarmaceuticoService {

	@Autowired
	IFarmaceuticoRepository _IFarmaceuticoRepository;

	public List<Farmaceutico> getAll() {
		return _IFarmaceuticoRepository.findAllByOrderByIdFarmaceuticoDesc();
	}
	
	public List<Farmaceutico> search(FarmaceuticoFilter filter) {
		return _IFarmaceuticoRepository.findAllWithFilters(filter.getTipoDoc());
	}

	@Transactional
	public ResultadoResponse create(Farmaceutico farmaceutico) {
		try {
			Farmaceutico registro = _IFarmaceuticoRepository.save(farmaceutico);
			String mensaje = String.format("Farmaceutico nro. %s registrado", registro.getIdFarmaceutico());
			return new ResultadoResponse(true, mensaje);
		} catch (Exception ex) {
			return new ResultadoResponse(false, "Error al registrar: " + ex.getMessage());
		}
	}
	
	public Farmaceutico getOne(int id) {
		return _IFarmaceuticoRepository.findById(id).orElseThrow();
	}
	
	@Transactional
	public ResultadoResponse update(Farmaceutico farmaceutico) {
		try {
			Farmaceutico registro = _IFarmaceuticoRepository.save(farmaceutico);
			String mensaje = String.format("Farmaceutico nro. %s actualizado", registro.getIdFarmaceutico());
			return new ResultadoResponse(true, mensaje);
		} catch (Exception ex) {
			return new ResultadoResponse(false, "Error al actualizar: " + ex.getMessage());
		}
	}
	
	@Transactional
	public ResultadoResponse delete(Integer id) {
		try {
			Farmaceutico registro = _IFarmaceuticoRepository.findById(id).orElseThrow();
	        if (registro == null) {
	            return new ResultadoResponse(false, "Farmaceutico no encontrado");
	        }

	        if (registro.getEstado().getIdEstado() == 1) {
	        	_IFarmaceuticoRepository.desactivarMedicamento(id);
	            return new ResultadoResponse(true, "Farmaceutico desactivado");
	        } else {
	        	_IFarmaceuticoRepository.activarMedicamento(id);
	            return new ResultadoResponse(true, "Farmaceutico activado");
	        }
	    } catch (Exception e) {
	        return new ResultadoResponse(false, "Error al cambiar estado: " + e.getMessage());
	    }
	}
	
	
}

