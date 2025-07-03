package com.Farmacia.ProyectoLP2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.repositories.IValidacionUsuario;
import com.Farmacia.ProyectoLP2.services.UsuarioService;
import com.Farmacia.ProyectoLP2.util.Alert;

@Controller
public class RegistroController {

	@Autowired
	private UsuarioService usuarioSerice;
	
	
	
	
	@GetMapping("/registro")
	public String nuevo(Model model) {
		model.addAttribute("usuario", new Usuario());
		model.addAttribute("pageTitle", "Crear Usuario");
		return "cliente/registro";
	}
	


	@PostMapping("/registrar")
	public String registrar(@Validated(IValidacionUsuario.class) @ModelAttribute("usuario") Usuario usuario,
	                        BindingResult bindingResult, Model model, RedirectAttributes flash) {

	    if (bindingResult.hasErrors()) {
	        model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
	        return "cliente/registro";
	    }

	    ResultadoResponse response = usuarioSerice.create(usuario);

	    if (!response.success) {
	        model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
	        return "cliente/registro";
	    }

	    String mensaje = Alert.sweetAlertSuccess("Gracias por registrarte " + usuario.getNombre());
	    flash.addFlashAttribute("alert", mensaje);

	    return "redirect:/login";
	}

}
