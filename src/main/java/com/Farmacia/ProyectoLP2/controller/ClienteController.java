package com.Farmacia.ProyectoLP2.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.Farmacia.ProyectoLP2.dto.MedicamentoFilterCliente;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.model.OrdenCompra;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.services.CategoriaService;
import com.Farmacia.ProyectoLP2.services.MedicamentoClienteService;
import com.Farmacia.ProyectoLP2.services.OrdenCompraService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@Controller
public class ClienteController {
	@Autowired
	private MedicamentoClienteService medicService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private OrdenCompraService compraService;

	@GetMapping("/")
	public String index(Model model) {
		List<Medicamento> destacados = medicService.getFeatured();
		destacados.forEach(m -> {
			if (m.getImagenBytes() != null) {
				String base64 = Base64.getEncoder().encodeToString(m.getImagenBytes());
				m.setBase64Img(base64);
			}
		});
		model.addAttribute("destacados", destacados);
		model.addAttribute("pageTitle", "Inicio");
		return "cliente/index";
	}

	@GetMapping("/medicamentos")
	public String medicamentos(@ModelAttribute MedicamentoFilterCliente filter,
			@PageableDefault(size = 12) Pageable pageable, Model model) {
		Page<Medicamento> pageMedicamentos = medicService.getMedicineforCategorie(filter, pageable);

		pageMedicamentos.getContent().forEach(m -> {
			if (m.getImagenBytes() != null) {
				String base64 = Base64.getEncoder().encodeToString(m.getImagenBytes());
				m.setBase64Img(base64);
			}
		});

		model.addAttribute("pageMedicamentos", pageMedicamentos);
		model.addAttribute("filter", filter);
		model.addAttribute("lstCategorias", categoriaService.getAll());
		model.addAttribute("pageTitle", "Medicamentos");
		return "cliente/medicamentos";
	}

	@GetMapping("/perfil")
	public String perfil(HttpSession session, Model model, RedirectAttributes redirectAttrs) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario == null) {
			redirectAttrs.addFlashAttribute("alert", Alert.sweetAlertInfo(
					"¡Ups! Necesitas iniciar sesión para poder ver tu perfil."));
			return "redirect:/login";
		}
		List<OrdenCompra> pedidos = compraService.getOrdenForIdUser(usuario.getIdUsuario());
		model.addAttribute("pageTitle", "Perfil");
		model.addAttribute("usuario", usuario);
		model.addAttribute("clientePedidos", pedidos);
		return "cliente/perfil";
	}
}
