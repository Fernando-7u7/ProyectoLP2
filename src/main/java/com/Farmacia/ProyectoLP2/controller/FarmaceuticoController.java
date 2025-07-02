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
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.services.UsuarioFarmaceuticoService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/mantenimiento/farmaceuticos")
public class FarmaceuticoController {

	@Autowired
	private UsuarioFarmaceuticoService farmaceuticoService;

	@GetMapping("/listado")
	public String listado(Model model) {

		List<Usuario> lstFarmaceuticos;
		model.addAttribute("pageTitle", "Farmaceutico");

		lstFarmaceuticos = farmaceuticoService.search();

		model.addAttribute("lstFarmaceuticos", lstFarmaceuticos);
		return "admin/mantenimiento/farmaceuticos/listado";
	}

	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		model.addAttribute("userFarma", new Usuario());
		model.addAttribute("pageTitle", "Crear Farmaceutico");
		return "admin/mantenimiento/farmaceuticos/nuevo";
	}

	@PostMapping("/registrar")
	public String registrar(@Valid @ModelAttribute("userFarma") Usuario userFarma, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {
		if (bindingResult.hasErrors()) {
		    model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "admin/mantenimiento/farmaceuticos/nuevo";
		}

		ResultadoResponse response = farmaceuticoService.create(userFarma);

		if (!response.success) {
			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "admin/mantenimiento/farmaceuticos/nuevo";
		}

		String mensaje = Alert
				.sweetAlertSuccess("Farmacéutico con código " + userFarma.getIdUsuario() + " registrado");
		flash.addFlashAttribute("alert", mensaje);
		return "redirect:/admin/mantenimiento/farmaceuticos/listado";
	}

	@GetMapping("/edicion/{id}")
	public String edicion(@PathVariable Integer id, Model model) {
		Usuario farmaceutico = farmaceuticoService.getOne(id);
		model.addAttribute("farmaceutico", farmaceutico);
		model.addAttribute("pageTitle", "Actualizar Farmaceutico");
		return "admin/mantenimiento/farmaceuticos/edicion";

	}

	@PostMapping("/guardar")
	public String guardar(@Valid @ModelAttribute("farmaceutico") Usuario farmaceutico, BindingResult bindingResult, Model model,
			RedirectAttributes flash) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("alert", Alert.sweetAlertInfo("Falta completar información"));
			return "admin/mantenimiento/farmaceuticos/edicion";
		}

		ResultadoResponse response = farmaceuticoService.update(farmaceutico);

		if (!response.success) {

			model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
			return "admin/mantenimiento/farmaceuticos/edicion";
		}

		String mensaje = Alert
				.sweetAlertSuccess("Farmacéutico con código " + farmaceutico.getIdUsuario() + " actualizado");
		flash.addFlashAttribute("alert", mensaje);
		return "redirect:/admin/mantenimiento/farmaceuticos/listado";
	}

	@PostMapping("/eliminar")
	public String toggleEstado(@RequestParam("idUsuario") Integer idFarmaceutico, RedirectAttributes flash) {
		ResultadoResponse response = farmaceuticoService.delete(idFarmaceutico);
		String alert = Alert.sweetAlertSuccess(response.mensaje);
		flash.addFlashAttribute("alert", alert);
		return "redirect:/admin/mantenimiento/farmaceuticos/listado";
	}

}
