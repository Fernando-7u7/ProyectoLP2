package com.Farmacia.ProyectoLP2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.models.Estado;
import com.Farmacia.ProyectoLP2.repositories.IEstadoRepository;

@Service
public class EstadoService {
	@Autowired
	private IEstadoRepository _EstadoRepository;
	
	public List<Estado> getAll(){
		return _EstadoRepository.findAll();
	}
}