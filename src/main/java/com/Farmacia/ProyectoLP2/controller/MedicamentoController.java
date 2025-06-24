package com.Farmacia.ProyectoLP2.controller;

import java.util.Base64;
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
import com.Farmacia.ProyectoLP2.model.Categoria;
import com.Farmacia.ProyectoLP2.model.Estado;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.model.Proveedor;
import com.Farmacia.ProyectoLP2.services.CategoriaService;
import com.Farmacia.ProyectoLP2.services.EstadoService;
import com.Farmacia.ProyectoLP2.services.MedicamentoService;
import com.Farmacia.ProyectoLP2.services.ProveedorService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/mantenimiento/medicamento")
public class MedicamentoController {

	@Autowired
	private MedicamentoService medicamentoService;

	@Autowired
	private ProveedorService proveedorService;

	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private EstadoService estadoService;

	@GetMapping("/listado")
	public String listMedicine(Model model) {
		List<Medicamento> lstMedicamento = medicamentoService.getAll();
		lstMedicamento.forEach(m -> {
	        if (m.getImagenBytes() != null) {
	            String base64 = Base64.getEncoder().encodeToString(m.getImagenBytes());
	            m.setBase64Img(base64);
	        }
	    });
		model.addAttribute("lstMedicamento", lstMedicamento);
		return "admin/mantenimiento/medicamento/listadoMedicamento";
	}

	@GetMapping("/nuevo")
	public String create(Model model) {
		loadLists(model);
		model.addAttribute("medicamento", new Medicamento());
		return "admin/mantenimiento/medicamento/nuevoMedicamento";
	}

	@PostMapping(value = "/registrar", consumes = "multipart/form-data")
	public String register(@Valid @ModelAttribute Medicamento m, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {
		
		if (bindingResult.hasErrors()) {
			loadLists(model);
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "admin/mantenimiento/medicamento/nuevoMedicamento";
		}

		ResultadoResponse response = medicamentoService.create(m);

		if (!response.success) {
			loadLists(model);
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "admin/mantenimiento/medicamento/nuevoMedicamento";
		}
		String alert = Alert.sweetAlertSuccess(response.mensaje);
		flash.addFlashAttribute("alert", alert);
		return "redirect:admin/mantenimiento/medicamento/listado";
	}
	
	@GetMapping("/editar/{id}")
	public String edit(@PathVariable String id, Model model) {
		loadLists(model);
		Medicamento m = medicamentoService.getOne(id);
		if (m.getImagenBytes() != null) {
	        String base64Img = Base64.getEncoder().encodeToString(m.getImagenBytes());
	        m.setBase64Img(base64Img);
	    }
		model.addAttribute("medicamento", m);
		return "admin/mantenimiento/medicamento/editarMedicamento";
	}

	@PostMapping(value = "/guardar", consumes = "multipart/form-data")
	public String save(@Valid @ModelAttribute Medicamento m, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {
		
		if (bindingResult.hasErrors()) {
			loadLists(model);
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "admin/mantenimiento/medicamento/editarMedicamento";
		}

		ResultadoResponse response = medicamentoService.update(m);
		
		if (!response.success) {
			loadLists(model);
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "admin/mantenimiento/medicamento/editarMedicamento";
		}
		String alert = Alert.sweetAlertSuccess(response.mensaje);
		flash.addFlashAttribute("alert", alert);
		return "redirect:admin/mantenimiento/medicamento/listado";
	}
	
	@PostMapping("/eliminar")
	public String toggleEstado(@RequestParam("idMedicamento") String idMedicamento, RedirectAttributes flash) {
	    ResultadoResponse response = medicamentoService.delete(idMedicamento);
	    String alert = Alert.sweetAlertSuccess(response.mensaje);
	    flash.addFlashAttribute("alert", alert);
	    return "redirect:admin/mantenimiento/medicamento/listado";
	}

	

	private void loadLists(Model model) {
		List<Proveedor> lstProveedor = proveedorService.getAll();
		List<Categoria> lstCategoria = categoriaService.getAll();
		List<Estado> lstEstado = estadoService.getAll();
		model.addAttribute("lstProveedor", lstProveedor);
		model.addAttribute("lstEstado", lstEstado);
		model.addAttribute("lstCategoria", lstCategoria);
	}

}
