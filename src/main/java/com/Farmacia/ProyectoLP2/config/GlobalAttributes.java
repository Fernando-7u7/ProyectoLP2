package com.Farmacia.ProyectoLP2.config;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class GlobalAttributes {

    @ModelAttribute
    public void setGlobalAttributes(Model model, HttpSession session) {
       
    }
    
    @ModelAttribute
    public void addGlobalAttributes(Model model, HttpServletRequest request) {
        model.addAttribute("requestURI", request.getRequestURI());
    }
}
