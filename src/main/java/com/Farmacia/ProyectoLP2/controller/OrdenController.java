package com.Farmacia.ProyectoLP2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Farmacia.ProyectoLP2.dto.MedicamentoSeleccionado;
import com.Farmacia.ProyectoLP2.services.CategoriaService;
import com.Farmacia.ProyectoLP2.services.MedicamentoService;
import com.Farmacia.ProyectoLP2.services.OrdenCompraService;
import com.Farmacia.ProyectoLP2.services.ProveedorService;
import com.Farmacia.ProyectoLP2.services.UsuarioService;

@Controller
@RequestMapping("/orden")
public class OrdenController {

	@Autowired
	private MedicamentoService medicamentoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private OrdenCompraService ordenService;
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	
	@ModelAttribute("seleccionados")
	public List<MedicamentoSeleccionado> inicializarSeleccionados() {
		return new ArrayList<>();
	}
	
	
	@GetMapping("/listado")
	public String listado(Model model) {
		model.addAttribute("contenidoFarmaceutico", "farmaceutico/ordenesListado :: contenido");
		model.addAttribute("lstBoletas",ordenService.search(3));
		return "farmaceutico/ordenesListado";
	}
}
