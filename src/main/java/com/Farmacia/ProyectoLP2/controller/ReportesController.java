package com.Farmacia.ProyectoLP2.controller;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Farmacia.ProyectoLP2.services.ReporteService;

import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@RestController
@RequestMapping("/reportes")
public class ReportesController {

	@Autowired
	private ReporteService reporteService;

	@GetMapping("/boleta")
	public void boletaReporte(@RequestParam Integer numBol, HttpServletResponse response) throws Exception {
		// Ruta del reporte (en resources/reportes)
		String reportPath = "/reportes/Boleta.jrxml";

		// Parámetros
		Map<String, Object> params = new HashMap<>();
		params.put("idBoleta", numBol);
		
		//Get JasperPrint
		JasperPrint jasperPrint = reporteService.getJasperPrint(params, reportPath);

		// Configuración de respuesta HTTP
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", String.format("inline; filename=boleta-nro-%s.pdf", numBol));

		// Exportar a PDF
		OutputStream outputStream = response.getOutputStream();
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		outputStream.flush();
		outputStream.close();
	}
	
	@GetMapping("/medicamentoReport")
	public void medicamentoStockReporte(HttpServletResponse response) throws Exception {
	    // Ruta del archivo .jrxml en resources/reportes/
	    String reportPath = "/reportes/StockMedicamento.jrxml";

	    // Parámetros vacíos porque no hay filtros
	    Map<String, Object> params = new HashMap<>();

	    // Obtener el reporte compilado y lleno con datos
	    JasperPrint jasperPrint = reporteService.getJasperPrint(params, reportPath);

	    // Configurar la respuesta HTTP para PDF en línea
	    response.setContentType("application/pdf");
	    String fileName = "reporte-stock-" + LocalDate.now() + ".pdf";
	    response.setHeader("Content-Disposition", "inline; filename=" + fileName);

	    // Enviar el PDF al navegador
	    try (OutputStream outputStream = response.getOutputStream()) {
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	    }
	}

}
