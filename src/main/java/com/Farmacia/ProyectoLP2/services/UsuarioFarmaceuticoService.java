package com.Farmacia.ProyectoLP2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.Rol;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.repositories.IRolRepository;
import com.Farmacia.ProyectoLP2.repositories.IUsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class UsuarioFarmaceuticoService {

	@Autowired
	private IUsuarioRepository _usuarioRepository;

	@Autowired
	private IRolRepository _rolRepository;

	public Usuario getOne(Integer id) {
		return _usuarioRepository.findById(id).orElseThrow();
	}

	public List<Usuario> getAll() {
		return _usuarioRepository.findAllByOrderByIdUsuarioDesc();
	}

	public List<Usuario> search() {
	       Rol rolFarmaceutico = _rolRepository.findById(3).orElse(null);
	       return _usuarioRepository.findByRol(rolFarmaceutico);
	   }

	@Transactional
	public ResultadoResponse create(Usuario usuario) {
	    try {

	        Rol rolFarmaceutico = _rolRepository.findById(3).orElse(null);
	        
	        if (rolFarmaceutico == null) {
	            return new ResultadoResponse(false, "Rol no encontrado");
	        }

	        usuario.setRol(rolFarmaceutico);

	        Usuario registro = _usuarioRepository.save(usuario);
	        String mensaje = String.format("Farmacéutico nro. %s registrado", registro.getIdUsuario());
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
				registro.setCorreo(usuario.getCorreo());
				registro.setDni(usuario.getDni());
				registro.setClave(usuario.getClave());
				registro.setDireccion(usuario.getDireccion());
				registro.setTelefono(usuario.getTelefono());
				registro.setClave(usuario.getClave());

				_usuarioRepository.save(registro);

				String mensaje = String.format("Farmacéutico nro. %s actualizado correctamente",
						registro.getIdUsuario());
				return new ResultadoResponse(true, mensaje);
			} else {
				return new ResultadoResponse(false, "Farmacéutico no encontrado");
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
				return new ResultadoResponse(false, "Farmacéutico no encontrado");
			}

			if (registro.getEstado().getIdEstado() == 1) {
				_usuarioRepository.desactivarUusario(id);
				return new ResultadoResponse(true, "Farmacéutico desactivado");
			} else {
				_usuarioRepository.activarUusario(id);
				return new ResultadoResponse(true, "Farmacéutico activado");
			}
		} catch (Exception e) {
			return new ResultadoResponse(false, "Error al cambiar estado: " + e.getMessage());
		}
	}
}
