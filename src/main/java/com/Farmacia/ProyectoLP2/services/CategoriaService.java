package com.Farmacia.ProyectoLP2.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.model.Categoria;
import com.Farmacia.ProyectoLP2.repositories.ICategoriaRepository;



@Service
public class CategoriaService {
	@Autowired
	private ICategoriaRepository categoriaRepo;
	
	public List<Categoria> getAll(){
		return categoriaRepo.findAll();
	}
	
	public long countCategories() {
		return categoriaRepo.count();
	}
}
