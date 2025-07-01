package com.Farmacia.ProyectoLP2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.repositories.IUsuario;

@Service
public class UsuarioService {

	@Autowired
	private IUsuario _usuarioRepository;
	
	public Usuario getOne(Integer id) {
		return _usuarioRepository.findById(id).orElseThrow();
	}
}
