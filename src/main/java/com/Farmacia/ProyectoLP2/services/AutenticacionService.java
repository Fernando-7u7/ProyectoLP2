package com.Farmacia.ProyectoLP2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.AutenticacionFilter;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.repositories.IUsuarioRepository;

@Service
public class AutenticacionService {

	@Autowired
	private IUsuarioRepository _usuarioRepository;
	
	public Usuario autenticar(AutenticacionFilter filter) {
		return _usuarioRepository.findByCorreoAndClave(filter.getCorreo(), filter.getClave());
	}
}
