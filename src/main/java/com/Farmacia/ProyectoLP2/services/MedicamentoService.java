package com.Farmacia.ProyectoLP2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.repositories.IMedicamentoRepository;

import jakarta.transaction.Transactional;

@Service
public class MedicamentoService {
	@Autowired
	private IMedicamentoRepository medicRepository;

	public List<Medicamento> getAll() {
		return medicRepository.findAllByOrderByIdMedicamentoDesc();
	}
	
	@Transactional
	public ResultadoResponse create(Medicamento m) {
		try {
			if (m.getImagen() != null && !m.getImagen().isEmpty()) {
				m.setImagenBytes(m.getImagen().getBytes());
			}

			medicRepository.createMedicine(m.getImagenBytes(), m.getNombre(), m.getPrecio(),
					m.getStockActual(),  m.getFechaVencimiento(), m.getDescripcion(),
					m.getProveedor().getIdProveedor(), m.getPreescripcion(), m.getCategoria().getIdCategoria());

			String message = "Medicamento registrado correctamente";
			return new ResultadoResponse(true, message);

		} catch (Exception e) {
			e.printStackTrace(); 
			return new ResultadoResponse(false, "Error al registrar " + e.getMessage());
		}
	}
	
	public Medicamento getOne(String id) {
		return medicRepository.findById(id).orElseThrow();
	}
	
	@Transactional
	public ResultadoResponse update(Medicamento m) {
		try {
			if (m.getImagen() != null && !m.getImagen().isEmpty()) {
				m.setImagenBytes(m.getImagen().getBytes());
			}

			medicRepository.updateMedicine(m.getIdMedicamento(), m.getImagenBytes(), m.getNombre(), m.getPrecio(),
					m.getStockActual(), m.getFechaVencimiento(), m.getDescripcion(),
					m.getProveedor().getIdProveedor(), m.getPreescripcion(), m.getCategoria().getIdCategoria());

			String message = "Medicamento actualizado correctamente";
			return new ResultadoResponse(true, message);

		} catch (Exception e) {
			e.printStackTrace(); 
			return new ResultadoResponse(false, "Error al registrar " + e.getMessage());
		}
	}
	
	@Transactional
	public ResultadoResponse delete(String id) {
		try {
	        Medicamento med = medicRepository.findById(id).orElseThrow();
	        if (med == null) {
	            return new ResultadoResponse(false, "Medicamento no encontrado");
	        }

	        if (med.getEstado().getIdEstado() == 1) {
	        	medicRepository.desactivarMedicamento(id);
	            return new ResultadoResponse(true, "Medicamento desactivado");
	        } else {
	        	medicRepository.activarMedicamento(id);
	            return new ResultadoResponse(true, "Medicamento activado");
	        }
	    } catch (Exception e) {
	        return new ResultadoResponse(false, "Error al cambiar estado: " + e.getMessage());
	    }
	}
}
