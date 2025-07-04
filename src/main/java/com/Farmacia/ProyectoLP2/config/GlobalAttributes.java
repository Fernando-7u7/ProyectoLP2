package com.Farmacia.ProyectoLP2.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Farmacia.ProyectoLP2.model.Carro;
import com.Farmacia.ProyectoLP2.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalAttributes {

	@ModelAttribute
	public void setGlobalAttributes(Model model, HttpSession session) {
	    Usuario usuario = (Usuario) session.getAttribute("usuario");
	    if (usuario != null) {
	        model.addAttribute("nombre", usuario.getNomsConApes());
	    }
	}
  
    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("requestURI", request.getRequestURI());
    }
    
    @ModelAttribute("carrito")
    public Carro carrito(HttpSession session) {
    	Carro carrito = (Carro) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carro();
        }
        return carrito;
    }
}
