package com.Farmacia.ProyectoLP2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.repositories.IUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	@Autowired
	private IUsuarioRepository _usuarioRepository;
	
	public Usuario getOne(Integer id) {
		return _usuarioRepository.findById(id).orElseThrow();
	}
	
	public List<Usuario> getAll() {
		return _usuarioRepository.findAllByOrderByIdUsuarioDesc();
	}

	
	@Transactional
	public ResultadoResponse create(Usuario usuario) {
		try {
			Usuario registro = _usuarioRepository.save(usuario);
			String mensaje = String.format("Usuario nro. %s registrado", registro.getIdUsuario());
			return new ResultadoResponse(true, mensaje);
		} catch (Exception ex) {
			return new ResultadoResponse(false, "Error al registrar: " + ex.getMessage());
		}
	}
	
	@Transactional
	public ResultadoResponse update(Usuario usuario) {
	    try {

	        Optional<Usuario> optional = _usuarioRepository.findById(usuario.getIdUsuario());
	        
	        if (optional.isPresent()) {
	        	Usuario registro = optional.get();

	        	registro.setNombre(usuario.getNombre());
	        	registro.setApellido(usuario.getApellido());
	        	registro.setCorreo(usuario.getApellido());
	        	registro.setDni(usuario.getDni());
	        	registro.setClave(usuario.getClave());
	        	registro.setDireccion(usuario.getDireccion());
	        	registro.setTelefono(usuario.getTelefono());
	        	registro.setRol(usuario.getRol());


	        	_usuarioRepository.save(registro);

	            String mensaje = String.format("Usuario nro. %s actualizado correctamente", registro.getIdUsuario());
	            return new ResultadoResponse(true, mensaje);
	        } else {
	            return new ResultadoResponse(false, "Usuario no encontrado");
	        }

	    } catch (Exception ex) {
	        return new ResultadoResponse(false, "Error al actualizar: " + ex.getMessage());
	    }
	}
	

	@Transactional
	public ResultadoResponse delete(Integer id) {
		try {
			Usuario registro = _usuarioRepository.findById(id).orElseThrow();
			if (registro == null) {
				return new ResultadoResponse(false, "Usuario no encontrado");
			}

			if (registro.getEstado().getIdEstado() == 1) {
				_usuarioRepository.desactivarUusario(id);
				return new ResultadoResponse(true, "Usuario desactivado");
			} else {
				_usuarioRepository.activarUusario(id);
				return new ResultadoResponse(true, "Usuario activado");
			}
		} catch (Exception e) {
			return new ResultadoResponse(false, "Error al cambiar estado: " + e.getMessage());
		}
	}
	 
    
}
