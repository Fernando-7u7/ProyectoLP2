package com.Farmacia.ProyectoLP2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.Estado;
import com.Farmacia.ProyectoLP2.model.Proveedor;
import com.Farmacia.ProyectoLP2.services.EstadoService;
import com.Farmacia.ProyectoLP2.services.ProveedorService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/admin/mantenimiento/proveedores")

public class ProveedorController {

	@Autowired
	private ProveedorService _proveedorService;
	
	@Autowired
	private EstadoService _estadoService;
	
	
	@GetMapping("/listado")
	public String listado(Model model) {

		List<Proveedor> lstProveedor = _proveedorService.getAll();
		model.addAttribute("lstProveedor", lstProveedor);
		return "admin/mantenimiento/proveedores/listadoProveedor";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {

		model.addAttribute("proveedor", _proveedorService.getAll());

		Proveedor proveedor = new Proveedor();

		model.addAttribute("proveedor", proveedor);
		return "admin/mantenimiento/proveedores/nuevoProveedor";
	}

	@PostMapping("/registrar")
	public String registrar(@Valid @ModelAttribute Proveedor proveedor,BindingResult result, Model model, RedirectAttributes flash) {
	    
		if (result.hasErrors()) {
	        model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
	        return "admin/mantenimiento/proveedores/nuevoProveedor"; 
	    }

	    try {
	        Estado estadoActivo = new Estado();
	        estadoActivo.setIdEstado(1);
	        proveedor.setEstado(estadoActivo);

	        Proveedor registrado = _proveedorService.create(proveedor);
	        String mensaje = String.format("Proveedor %s registrado correctamente.", registrado.getRazonSocial());
	        flash.addFlashAttribute("alert", Alert.sweetToast(mensaje, "success", 5000));
	        return "redirect:/admin/mantenimiento/proveedores/listado";

	    } catch (Exception ex) {
	        model.addAttribute("alert", Alert.sweetAlertError("Error al registrar: " + ex.getMessage()));
	        return "admin/mantenimiento/proveedores/nuevoProveedor";
	    }
	}

	@GetMapping("/edicion/{idProveedor}")
	public String edicion(@PathVariable Integer idProveedor, Model model) {

		Proveedor proveedor = _proveedorService.getOne(idProveedor);
		model.addAttribute("proveedor", proveedor);
		
		List<Estado> estados = _estadoService.getAll();
		model.addAttribute("lstEstados", estados);
		
		return "admin/mantenimiento/proveedores/editarProveedor";
	}

	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Proveedor proveedor, BindingResult bindingResult, Model model, RedirectAttributes flash) {
	    if (bindingResult.hasErrors()) {
	        model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
	        return "admin/mantenimiento/proveedores/editarProveedor";
	    }

	    try {
	        Proveedor actualizado = _proveedorService.update(proveedor);

	        String mensaje = String.format("Proveedor nro. %s actualizado correctamente.", actualizado.getIdProveedor());
	        flash.addFlashAttribute("alert", Alert.sweetToast(mensaje, "success", 5000));

	        return "redirect:/admin/mantenimiento/proveedores/listado";

	    } catch (Exception ex) {
	        model.addAttribute("alert", Alert.sweetAlertError("Error al actualizar: " + ex.getMessage()));
	        return "admin/mantenimiento/proveedores/editarProveedor";
	    }
	}

	
	@PostMapping("/eliminar")
	public String toggleEstado(@RequestParam("idProveedor") Integer idProveedor, RedirectAttributes flash) {
	    ResultadoResponse response = _proveedorService.delete(idProveedor);
	    String alert = Alert.sweetAlertSuccess(response.mensaje);
	    flash.addFlashAttribute("alert", alert);
	    return "redirect:/admin/mantenimiento/proveedores/listado";
	}

}
