package com.Farmacia.ProyectoLP2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Farmacia.ProyectoLP2.dto.AutenticacionFilter;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.services.AutenticacionService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

	@Autowired
	private AutenticacionService autenticacionService;

	@GetMapping({"/login" })
	public String login(Model model) {
		model.addAttribute("filter", new AutenticacionFilter());
		return "cliente/login";
	}

	@PostMapping("/iniciar-sesion")
	public String iniciarSesion(@ModelAttribute AutenticacionFilter filter, HttpSession session, Model model,
			RedirectAttributes flash) {
		Usuario usuarioValidado = autenticacionService.autenticar(filter);

		if (usuarioValidado == null) {
			model.addAttribute("filter", new AutenticacionFilter());
			model.addAttribute("alert", Alert.sweetAlertError("Usuario y/o clave incorrecta"));
			return "cliente/login";
		}

		String nombreCompleto = String.format("%s %s", usuarioValidado.getNombre(), usuarioValidado.getApellido());
		session.setAttribute("idUsuario", usuarioValidado.getIdUsuario());
		session.setAttribute("nombre", nombreCompleto);
		session.setAttribute("correo", usuarioValidado.getCorreo());
		session.setAttribute("rol", usuarioValidado.getRol());

		String alert = Alert.sweetAlertSuccess("Bienvenido" + usuarioValidado.getNombre());
		flash.addFlashAttribute("alert", alert);
		
		if(usuarioValidado.getRol().getIdRol() == 1) {
			return "redirect:/admin/dashboard";
		}else {
		return "redirect:/index";
		}
	}

	@GetMapping("/index")
	public String home(HttpSession session, Model model) {
		if (session.getAttribute("correo") == null)
			return "redirect:/login";
		return "cliente/index";
	}

	@PostMapping("/cerrar-sesion")
	public String cerrarSesion(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@PostMapping("/cerrar-sesion-admin")
	public String cerrarSesionAdmin(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}
}
