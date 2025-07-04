package com.Farmacia.ProyectoLP2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.MedicamentoFilterCliente;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.repositories.IMedicamentoClienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class MedicamentoClienteService {
	@Autowired
	private IMedicamentoClienteRepository medicRepo;

	public List<Medicamento> getFeatured() {
		return medicRepo.findMedicinesMoreSales();
	}

	public Page<Medicamento> getMedicineforCategorie(MedicamentoFilterCliente filter, Pageable pageable) {
		if (filter.getIdCategorias() == null || filter.getIdCategorias().isEmpty()) {
			return medicRepo.findMedicinesActive(pageable);
		} else {
			return medicRepo.findAllWithFilters(filter.getIdCategorias(), pageable);
		}
	}
	
	public Medicamento getOne(String id) {
		return medicRepo.findById(id).orElseThrow();
	}

}
