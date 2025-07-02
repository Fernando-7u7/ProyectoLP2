package com.Farmacia.ProyectoLP2.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Farmacia.ProyectoLP2.model.Carro;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalAttributes {

    @ModelAttribute
    public void setGlobalAttributes(Model model, HttpSession session) {
    	Object nombre = session.getAttribute("nombre");
		if (nombre != null) {
			model.addAttribute("nombre", nombre);
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
