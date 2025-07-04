package com.Farmacia.ProyectoLP2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Farmacia.ProyectoLP2.dto.DetalleCompraPK;
import com.Farmacia.ProyectoLP2.dto.MedicamentoSeleccionado;
import com.Farmacia.ProyectoLP2.dto.OrdenFechaFilter;
import com.Farmacia.ProyectoLP2.dto.ResultadoResponse;
import com.Farmacia.ProyectoLP2.model.DetalleCompra;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.model.OrdenCompra;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.services.CategoriaService;
import com.Farmacia.ProyectoLP2.services.MedicamentoService;
import com.Farmacia.ProyectoLP2.services.OrdenCompraService;
import com.Farmacia.ProyectoLP2.services.ProveedorService;
import com.Farmacia.ProyectoLP2.services.UsuarioService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/orden")
@SessionAttributes("seleccionados")
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
	
	@GetMapping("/nuevo")
	public String nuevo(Model model) {
		System.out.println("Entrando a /orden/nuevo");
		model.addAttribute("medicamentos", medicamentoService.getActivos());
		model.addAttribute("medicamentoSeleccionado", new  MedicamentoSeleccionado());
		return "farmaceutico/nuevo";
	}


	@PostMapping("/agregar")
	public String agregar(@Valid @ModelAttribute MedicamentoSeleccionado seleccionado, BindingResult bindingResult,
			@ModelAttribute("seleccionados") List<MedicamentoSeleccionado> seleccionados, Model model) {
		
		model.addAttribute("medicamentos", medicamentoService.getActivos());
		model.addAttribute("medicamentoSeleccionado", seleccionado);
		
		if (bindingResult.hasErrors()) {
			String mensaje = obtenerMensajeValidacionAgregar(bindingResult);
			model.addAttribute("alert", Alert.sweetAlertInfo(mensaje));
			return "farmaceutico/nuevo";
		}
		
		boolean existe = seleccionados.stream().anyMatch(m -> m.getCodProducto().equals(seleccionado.getCodProducto()));

		if (existe) {
			model.addAttribute("alert", Alert.sweetAlertInfo("El medicamento se encuentra en la lista"));
			return "farmaceutico/nuevo";
		}

		seleccionados.add(seleccionado);	
		
		model.addAttribute("medicamentoSeleccionado", new  MedicamentoSeleccionado());
		return "farmaceutico/nuevo";
	}
	
	@PostMapping("/quitar")
	public String quitar(@RequestParam String codigo,
			@ModelAttribute("seleccionados") List<MedicamentoSeleccionado> seleccionados, Model model) {

		seleccionados.removeIf(p -> p.getCodProducto().equals(codigo));

		model.addAttribute("medicamentos", medicamentoService.getActivos());
		model.addAttribute("medicamentoSeleccionado", new MedicamentoSeleccionado());
		return "farmaceutico/nuevo";
	}

	@PostMapping("/registrar")
	public String registrar(@ModelAttribute OrdenCompra orden, @ModelAttribute("seleccionados") List<MedicamentoSeleccionado> seleccionados, Model model,
	        RedirectAttributes flash, HttpSession session, SessionStatus status) {
	    model.addAttribute("medicamentos", medicamentoService.getActivos());
	    model.addAttribute("medicamentoSeleccionado", new MedicamentoSeleccionado());

	    // Obtenemos dato de sesión
	    Integer idUsuario = (Integer) session.getAttribute("idUsuario");
	    
	    // Validamos sesión
	    if (idUsuario == null) {
	        flash.addFlashAttribute("alert", Alert.sweetAlertError("Sesión expirada"));
	        return "redirect:/login";
	    }
	    
	    // Obtenemos al usuario y lo seteamos
	    Usuario usuario = usuarioService.getOne(idUsuario); 
	    orden.setUsuario(usuario);
	    
	    // Validamos que al menos haya un seleccionado
	    if (seleccionados.isEmpty()) {
	        model.addAttribute("alert", Alert.sweetAlertInfo("Agregue un medicamento por lo menos"));
	        return "farmaceutico/nuevo";
	    }
	    
	    // Mapeamos los datos seleccionados a DetalleCompra y lo seteamos
	    List<DetalleCompra> lstDetalleOrdenes = seleccionados.stream().map(item -> {
	        Medicamento medicamento = medicamentoService.getOne(item.getCodProducto());
	        Integer cantidad = item.getCantidad();
	        Double precio = item.getPrecio();

	        DetalleCompra detalle = new DetalleCompra();
	        DetalleCompraPK pk = new DetalleCompraPK();
	               
	        // Aquí asumimos que orden.getId() ya tiene valor
	        pk.setIdMedicamento(medicamento.getIdMedicamento());
	        detalle.setId(pk);

	        detalle.setMedicamento(medicamento);
	        detalle.setCantidad(cantidad);
	        detalle.setPrecio(precio);
	        detalle.setOrdenCompra(orden);

	        return detalle;
	    }).toList();

	    
	    orden.setDetalles(lstDetalleOrdenes);
	    
	    ResultadoResponse response = ordenService.create(orden);
	    if (!response.success) {
	        model.addAttribute("alert", Alert.sweetAlertError(response.mensaje));
	        return "farmaceutico/nuevo";
	    }
	    String toast = Alert.sweetToast(response.mensaje, "success", 5000);
	    flash.addFlashAttribute("toast", toast);
	    status.setComplete();
	    return "redirect:/orden/listado";
	}

	private String obtenerMensajeValidacionAgregar(BindingResult bindingResult) {
		if (bindingResult.hasFieldErrors("codProducto"))
			return bindingResult.getFieldError("codProducto").getDefaultMessage();

		if (bindingResult.hasFieldErrors("precio"))
			return bindingResult.getFieldError("precio").getDefaultMessage();
		
		if (bindingResult.hasFieldErrors("descripcion"))
			return bindingResult.getFieldError("descripcion").getDefaultMessage();

		if (bindingResult.hasFieldErrors("cantidad"))
			return bindingResult.getFieldError("cantidad").getDefaultMessage();
		return null;
	}
}
