package com.Farmacia.ProyectoLP2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dtos.FarmaceuticoFilter;
import com.Farmacia.ProyectoLP2.dtos.ResultadoResponse;
import com.Farmacia.ProyectoLP2.models.Farmaceutico;
import com.Farmacia.ProyectoLP2.repositories.IFarmaceuticoRepository;

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
	
	public ResultadoResponse update(Farmaceutico farmaceutico) {
		try {
			Farmaceutico registro = _IFarmaceuticoRepository.save(farmaceutico);
			String mensaje = String.format("Farmaceutico nro. %s actualizado", registro.getIdFarmaceutico());
			return new ResultadoResponse(true, mensaje);
		} catch (Exception ex) {
			return new ResultadoResponse(false, "Error al actualizar: " + ex.getMessage());
		}
	}
}

