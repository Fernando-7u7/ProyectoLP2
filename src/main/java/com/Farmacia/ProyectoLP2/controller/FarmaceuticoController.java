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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Farmacia.ProyectoLP2.dto.FarmaceuticoFilter;
import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.Farmaceutico;
import com.Farmacia.ProyectoLP2.services.EstadoService;
import com.Farmacia.ProyectoLP2.services.FarmaceuticoService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/farmaceuticos")
public class FarmaceuticoController {

	@Autowired
	private FarmaceuticoService farmaceuticoService;
	
	@Autowired
	private EstadoService estadoService;

	@GetMapping("/listado")
	public String listado(@ModelAttribute FarmaceuticoFilter filter, Model model) {

		if (filter == null) {
			filter = new FarmaceuticoFilter();
		}

		List<Farmaceutico> lstFarmaceuticos;

		if (filter.getTipoDoc() != null && !filter.getTipoDoc().isEmpty()) {
			lstFarmaceuticos = farmaceuticoService.search(filter);
		} else {
			lstFarmaceuticos = farmaceuticoService.getAll();
		}

		model.addAttribute("lstFarmaceuticos", lstFarmaceuticos);
		model.addAttribute("filter", filter);
		return "farmaceuticos/listado";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("estado", estadoService.getAll());
		model.addAttribute("farmaceutico", new Farmaceutico());
		return "farmaceuticos/nuevo";
	}

	@PostMapping("/registrar")
	public String registrar(@Valid @ModelAttribute Farmaceutico farmaceutico, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("estado", estadoService.getAll());
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "farmaceuticos/nuevo";
		}

		ResultadoResponse response = farmaceuticoService.create(farmaceutico);

		if (!response.success) {
			model.addAttribute("estado", estadoService.getAll());
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "farmaceuticos/nuevo";
		}

		String mensaje = Alert.sweetAlertSuccess("Farmacéutico con código " + farmaceutico.getIdFarmaceutico() + " registrado");		
		flash.addFlashAttribute("alert", mensaje);
		
		return "redirect:/farmaceuticos/listado";
	}

	@GetMapping("/edicion/{id}")
	public String edicion(@PathVariable Integer id, Model model) {
		model.addAttribute("estado", estadoService.getAll());
		Farmaceutico farmaceutico = farmaceuticoService.getOne(id);
		model.addAttribute("farmaceutico", farmaceutico);
		return "farmaceuticos/edicion";
	}

	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute Farmaceutico farmaceutico, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("estado", estadoService.getAll());
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "farmaceuticos/edicion";
		}

		ResultadoResponse response = farmaceuticoService.update(farmaceutico);

		if (!response.success) {
			model.addAttribute("estado", estadoService.getAll());
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "farmaceuticos/edicion";
		}

		String mensaje = Alert.sweetAlertSuccess("Farmacéutico con código " + farmaceutico.getIdFarmaceutico() + " actualizado");		
		flash.addFlashAttribute("alert",mensaje);
		
		return "redirect:/farmaceuticos/listado";
	}

}
