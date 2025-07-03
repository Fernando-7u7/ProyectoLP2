package com.Farmacia.ProyectoLP2.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Farmacia.ProyectoLP2.model.Carro;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.model.OrdenCompra;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.services.MedicamentoClienteService;
import com.Farmacia.ProyectoLP2.services.OrdenCompraService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/carrito")
public class CarroController {
	@Autowired
	private MedicamentoClienteService medicamentoClienteService;

	@Autowired
	private OrdenCompraService ordenService;
	

	@GetMapping("/verCompras")
	public String verCarrito(HttpSession session, Model model) {
	    Carro carrito = (Carro) session.getAttribute("carrito");
	    Usuario usuario = (Usuario) session.getAttribute("usuario");

	    if (carrito == null) {
	        carrito = new Carro();
	        session.setAttribute("carrito", carrito);
	    }
	    model.addAttribute("carrito", carrito);

	    Double total = carrito.getItems().values().stream()
	            .mapToDouble(item -> item.getSubTotal() != null ? item.getSubTotal() : 0).sum();
	    
	    model.addAttribute("pageTitle", "Carrito");
	    model.addAttribute("total", total);
	    model.addAttribute("usuario", usuario);   
	    return "cliente/carro";
	}


	@PostMapping("/agregar")
	public String agregarProducto(@RequestParam String idMedicamento, @RequestParam int cantidad, HttpSession session,
			RedirectAttributes redirectAttributes) {
		Carro carrito = (Carro) session.getAttribute("carrito");
		if (carrito == null) {
			carrito = new Carro();
		}
		Medicamento medicamento = medicamentoClienteService.getOne(idMedicamento);

		String alert = carrito.agregarProducto(medicamento, cantidad);
		session.setAttribute("carrito", carrito);

		if (alert != null) {
			redirectAttributes.addFlashAttribute("alert", alert);
		}
		return "redirect:/medicamentos";
	}

	// Eliminar producto
	@PostMapping("/eliminar")
	public String eliminarProducto(@RequestParam String idMedicamento, HttpSession session) {
		Carro carrito = (Carro) session.getAttribute("carrito");
		if (carrito != null) {
			carrito.eliminarProducto(idMedicamento);
			session.setAttribute("carrito", carrito);
		}
		return "cliente/carro";
	}

	@PostMapping("/aumentar")
	public String aumentarCantidad(@RequestParam String idMedicamento, HttpSession session, RedirectAttributes redirectAttrs) {
	    Carro carrito = (Carro) session.getAttribute("carrito");
	    if (carrito == null) {
	        carrito = new Carro();
	    }

	    String mensaje = carrito.aumentarCantidad(idMedicamento);
	    session.setAttribute("carrito", carrito);

	    if (mensaje != null) {
	        redirectAttrs.addFlashAttribute("alert", mensaje);
	    }
	    return "redirect:/carrito/verCompras";
	}

	@PostMapping("/disminuir")
	public String disminuirCantidad(@RequestParam String idMedicamento, HttpSession session, RedirectAttributes redirectAttrs) {
	    Carro carrito = (Carro) session.getAttribute("carrito");
	    if (carrito == null) {
	        carrito = new Carro();
	    }

	    String mensaje = carrito.disminuirCantidad(idMedicamento);
	    session.setAttribute("carrito", carrito);

	    if (mensaje != null) {
	        redirectAttrs.addFlashAttribute("alert", mensaje);
	    }
	    return "redirect:/carrito/verCompras";
	}
	
	@PostMapping("/procesar")
	public String procesarCompra(HttpSession session, RedirectAttributes redirectAttrs) {
		Carro carrito = (Carro) session.getAttribute("carrito");
		Usuario usuario = (Usuario) session.getAttribute("usuario");

		if (usuario == null) {
			redirectAttrs.addFlashAttribute("alert", Alert.sweetAlertInfo(
					"¡Ups! Necesitas iniciar sesión para completar tu compra. Por favor, ingresa para continuar."));
			return "redirect:/login";
		}

		if (carrito == null || carrito.getItems().isEmpty()) {
			redirectAttrs.addFlashAttribute("error", "El carrito está vacío.");
			return "redirect:/carrito";
		}

		OrdenCompra orden = new OrdenCompra();
		orden.setFecha(LocalDate.now());
		orden.setUsuario(usuario);

		ordenService.guardarOrdenConDetalles(orden, carrito.getItems().values());

		carrito.limpiar();
		session.setAttribute("carrito", carrito);

		redirectAttrs.addFlashAttribute("alert", Alert.sweetAlertSuccess("Compra procesada correctamente."));
		return "redirect:/";
	}
}
