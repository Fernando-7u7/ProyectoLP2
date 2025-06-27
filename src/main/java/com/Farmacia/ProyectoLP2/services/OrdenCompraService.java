package com.Farmacia.ProyectoLP2.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.IMonthlySale;
import com.Farmacia.ProyectoLP2.repositories.IOrdenCompraRepository;

@Service
public class OrdenCompraService {
	@Autowired
	private IOrdenCompraRepository ordenCompraRepo;
	
	public List<IMonthlySale> getSalesForSixMonthlys() {
	    LocalDate fechaLimite = LocalDate.now()
	        .withDayOfMonth(1)
	        .minusMonths(5);
	    List<IMonthlySale> ventas = ordenCompraRepo.getSalesForMonthlys(fechaLimite);
	    return ventas.stream()
	                 .sorted(Comparator.comparing(IMonthlySale::getMes))
	                 .limit(6)
	                 .collect(Collectors.toList());
	}
}
