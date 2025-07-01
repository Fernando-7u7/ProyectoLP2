package com.Farmacia.ProyectoLP2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Farmacia.ProyectoLP2.model.Usuario;

public interface IUsuario  extends JpaRepository<Usuario, Integer>{

	Usuario findByCorreoAndClave(String correo, String clave);
}
