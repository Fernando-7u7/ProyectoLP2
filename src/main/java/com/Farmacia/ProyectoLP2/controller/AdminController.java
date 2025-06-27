package com.Farmacia.ProyectoLP2.controller;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Farmacia.ProyectoLP2.dto.IMonthlySale;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.services.CategoriaService;
import com.Farmacia.ProyectoLP2.services.MedicamentoService;
import com.Farmacia.ProyectoLP2.services.OrdenCompraService;


@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private MedicamentoService medicService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private OrdenCompraService ordenCompraService;
	
	@GetMapping("/dashboard")
	public String dashboardIndex(Model model) {
		List<Medicamento> lstMedicamento = medicService.expiredMedicamento();
		long totalMedicamento = medicService.countMedicine();
		long stockBajo = medicService.countMedicineStockLow();
		long totalCategoria = categoriaService.countCategories();
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
		model.addAttribute("ventasUltimos6Meses", ventasUltimos6Meses);

		return "admin/dashboard";
	}
}
