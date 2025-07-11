package com.Farmacia.ProyectoLP2.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.Farmacia.ProyectoLP2.dto.IMonthlySale;
import com.Farmacia.ProyectoLP2.dto.RoleFilter;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.model.OrdenCompra;
import com.Farmacia.ProyectoLP2.model.Rol;
import com.Farmacia.ProyectoLP2.model.Usuario;
import com.Farmacia.ProyectoLP2.services.CategoriaService;
import com.Farmacia.ProyectoLP2.services.MedicamentoService;
import com.Farmacia.ProyectoLP2.services.OrdenCompraService;
import com.Farmacia.ProyectoLP2.services.ProveedorService;
import com.Farmacia.ProyectoLP2.services.RolService;
import com.Farmacia.ProyectoLP2.util.Alert;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private MedicamentoService medicService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private OrdenCompraService ordenCompraService;
	
	@Autowired
	private ProveedorService proveedorService; 
	
	@Autowired
	private RolService rolService;
	
	@GetMapping("/dashboard")
	public String dashboardIndex(Model model,HttpSession session, RedirectAttributes redirectAttrs) {
		Usuario usuario = (Usuario) session.getAttribute("usuario");
		if (usuario == null) {
			redirectAttrs.addFlashAttribute("alert",
					Alert.sweetAlertInfo("¡Ups! Sesión expirada."));
			return "redirect:/login";
		}
		List<Medicamento> lstMedicamento = medicService.expiredMedicamento();
		long totalMedicamento = medicService.countMedicine();
		long stockBajo = medicService.countMedicineStockLow();
		long totalCategoria = categoriaService.countCategories();
		long totalProveedor = proveedorService.countProveedores();
		List<IMonthlySale> ventasUltimos6Meses = ordenCompraService.getSalesForSixMonthlys();
		lstMedicamento.forEach(m -> {
	        if (m.getImagenBytes() != null) {
	            String base64 = Base64.getEncoder().encodeToString(m.getImagenBytes());
	            m.setBase64Img(base64);
	        }
	    });
		model.addAttribute("pageTitle", "Dashboard");
		model.addAttribute("lstMedicamento", lstMedicamento);
		model.addAttribute("totalMedicamento", totalMedicamento);
		model.addAttribute("stockBajo", stockBajo);
		model.addAttribute("totalCategoria", totalCategoria);
		model.addAttribute("totalProveedor", totalProveedor);
		model.addAttribute("ventasUltimos6Meses", ventasUltimos6Meses);

		return "admin/dashboard";
	}
	
	@GetMapping("/ordenes")
	public String ordenCompra(@ModelAttribute RoleFilter filter,Model model) {
		List<OrdenCompra> pedidos = ordenCompraService.searchAdmin(filter);
		List<Rol> roles = rolService.getAll();
		model.addAttribute("lstPedidos",pedidos);
		model.addAttribute("lstRoles",roles);
	    model.addAttribute("filter", filter); 
		model.addAttribute("pageTitle", "Orden Compras");
		return "admin/ordenesCompras";
	}
}
