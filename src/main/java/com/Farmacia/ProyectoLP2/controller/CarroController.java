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
import com.Farmacia.ProyectoLP2.services.MedicamentoClienteService;
import com.Farmacia.ProyectoLP2.services.OrdenCompraService;

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

	    if (carrito == null) {
	        carrito = new Carro();
	        session.setAttribute("carrito", carrito);
	    }
	    model.addAttribute("carrito", carrito);
	    OrdenCompra ordenCompra = new OrdenCompra();
	    Double total = carrito.getItems().values().stream()
	                         .mapToDouble(item -> item.getSubTotal() != null ? item.getSubTotal() : 0)
	                         .sum();
	    ordenCompra.setDetalles(null);
	    model.addAttribute("pageTitle", "Carrito");

	    model.addAttribute("total", total);

	    return "cliente/carro";
	}
	
	@PostMapping("/agregar")
    public String agregarProducto(@RequestParam String idMedicamento, @RequestParam int cantidad, HttpSession session) {
        Carro carrito = (Carro) session.getAttribute("carrito");
        if (carrito == null) {
            carrito = new Carro();
        }
        Medicamento medicamento = medicamentoClienteService.getOne(idMedicamento);
        carrito.agregarProducto(medicamento, cantidad);
        session.setAttribute("carrito", carrito);
        return "redirect:/medicamentos";  
    }

    // Eliminar producto
    @PostMapping("/eliminar")
    public String eliminarProducto(@RequestParam String idMedicamento, HttpSession session) {
    	Carro carrito = (Carro) session.getAttribute("carrito");
        if(carrito != null) {
            carrito.eliminarProducto(idMedicamento);
            session.setAttribute("carrito", carrito);
        }
	    return "cliente/carro";
    }
    
    @PostMapping("/procesar")
    public String procesarCompra(HttpSession session, RedirectAttributes redirectAttrs) {
    	Carro carrito = (Carro) session.getAttribute("carrito");

        if (carrito == null || carrito.getItems().isEmpty()) {
            redirectAttrs.addFlashAttribute("error", "El carrito está vacío.");
            return "redirect:/carrito";
        }

        // Aquí debes implementar la lógica para guardar la orden y detalles en la base de datos.
        // Ejemplo básico:
        OrdenCompra orden = new OrdenCompra();
        orden.setFecha(LocalDate.now());
        orden.setUsuario(null); // ejemplo - CORREGIR NO ES ULL

        // Aquí asumes que tienes un servicio para guardar la orden y detalles
        ordenService.guardarOrdenConDetalles(orden, carrito.getItems().values());

        // Limpiar carrito después de procesar la compra
        carrito.limpiar();
        session.setAttribute("carrito", carrito);

        redirectAttrs.addFlashAttribute("success", "Compra procesada correctamente.");
        return "redirect:/";
    }
}
