package com.Farmacia.ProyectoLP2.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Farmacia.ProyectoLP2.dto.DetalleCompraPK;
import com.Farmacia.ProyectoLP2.dto.IMonthlySale;
import com.Farmacia.ProyectoLP2.dto.OrdenFechaFilter;
import com.Farmacia.ProyectoLP2.model.DetalleCompra;
import com.Farmacia.ProyectoLP2.model.Medicamento;
import com.Farmacia.ProyectoLP2.model.OrdenCompra;
import com.Farmacia.ProyectoLP2.model.Rol;
import com.Farmacia.ProyectoLP2.repositories.IDetalleCompraRepository;
import com.Farmacia.ProyectoLP2.repositories.IMedicamentoClienteRepository;
import com.Farmacia.ProyectoLP2.repositories.IOrdenCompraRepository;
import com.Farmacia.ProyectoLP2.repositories.IRolRepository;

import jakarta.transaction.Transactional;

@Service
public class OrdenCompraService {
	@Autowired
	private IOrdenCompraRepository ordenCompraRepo;
	
	@Autowired
	private IDetalleCompraRepository detalleCompraRepo;
	
	@Autowired
	private IRolRepository rolRepository;
	
	public List<OrdenCompra> search(Integer idRol){
	    Rol rol = rolRepository.findById(idRol).orElse(null);
	    return ordenCompraRepo.findByRol(rol);
	}
	
    public List<OrdenCompra> searchByFecha(OrdenFechaFilter filterFecha, Integer idRol) {
	    Rol rol = rolRepository.findById(idRol).orElse(null);
        return ordenCompraRepo.findByFechaAndRol(filterFecha.getFechaIni(), filterFecha.getFechaFin(), rol);
    }
	
	@Autowired
	private IMedicamentoClienteRepository medicamentoClienteRepository;
	
	public List<OrdenCompra> getOrdenForIdUser(Integer idUsuario) {
        return ordenCompraRepo.findByUsuarioId(idUsuario);
    }
	
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
	
	@Transactional
	public void guardarOrdenConDetalles(OrdenCompra orden, Collection<DetalleCompra> detalles) {
	    ordenCompraRepo.save(orden);

	    for (DetalleCompra detalle : detalles) {
	        Medicamento medicamento = detalle.getMedicamento();
	        int cantidadComprada = detalle.getCantidad();

	        if (medicamento.getStockActual() < cantidadComprada) {
	            throw new IllegalStateException("Stock insuficiente para el medicamento: " + medicamento.getNombre());
	        }

	        medicamento.setStockActual(medicamento.getStockActual() - cantidadComprada);
	        medicamentoClienteRepository.save(medicamento);

	        detalle.setOrdenCompra(orden);
	        DetalleCompraPK pk = new DetalleCompraPK();
	        pk.setIdOrden(orden.getIdOrden());
	        pk.setIdMedicamento(medicamento.getIdMedicamento());
	        detalle.setId(pk);

	        detalleCompraRepo.save(detalle);
	    }
	}

	
}
