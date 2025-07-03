package com.Farmacia.ProyectoLP2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Farmacia.ProyectoLP2.dto.MedicamentoFilter;
import com.Farmacia.ProyectoLP2.dto.MedicamentoSeleccionado;
import com.Farmacia.ProyectoLP2.dto.OrdenFechaFilter;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.model.OrdenCompra;
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
	public String listado(@ModelAttribute OrdenFechaFilter filterFecha, Model model) {
		List<OrdenCompra> lstOrdenes;

		if (filterFecha.getFechaIni() != null && filterFecha.getFechaFin() != null) {

			lstOrdenes = ordenService.searchByFecha(filterFecha, 3);
		} else {

			lstOrdenes = ordenService.search(3);
		}
		model.addAttribute("contenidoFarmaceutico", "farmaceutico/ordenesListado :: contenido");
		model.addAttribute("filterFecha", filterFecha);
		model.addAttribute("lstOrdenes", lstOrdenes);

		return "farmaceutico/ordenesListado";
	}

}
