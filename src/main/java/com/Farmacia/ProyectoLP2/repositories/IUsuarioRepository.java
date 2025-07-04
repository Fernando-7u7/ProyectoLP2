package com.Farmacia.ProyectoLP2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Farmacia.ProyectoLP2.model.Rol;
import com.Farmacia.ProyectoLP2.model.Usuario;

public interface IUsuarioRepository  extends JpaRepository<Usuario, Integer>{

	Usuario findByCorreoAndClave(String correo, String clave);
	
	List<Usuario> findAllByOrderByIdUsuarioDesc();

	   @Query("""
	           SELECT u FROM Usuario u
	           WHERE
	           (:rol is null or u.rol = :rol)
	           ORDER BY
	               u.idUsuario DESC
	           """)
	   List<Usuario> findByRol(Rol rol);

	
	@Modifying
	@Query("UPDATE Usuario u SET u.estado.id = 1 WHERE u.idUsuario = :id")
	void activarUusario(@Param("id") Integer id);

	@Modifying
	@Query("UPDATE Usuario u SET u.estado.id = 2 WHERE u.idUsuario = :id")
	void desactivarUusario(@Param("id") Integer id);

}
