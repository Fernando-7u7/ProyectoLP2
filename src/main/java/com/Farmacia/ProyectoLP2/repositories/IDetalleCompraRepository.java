package com.Farmacia.ProyectoLP2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Farmacia.ProyectoLP2.dto.DetalleCompraPK;
import com.Farmacia.ProyectoLP2.model.DetalleCompra;

public interface IDetalleCompraRepository extends JpaRepository<DetalleCompra, DetalleCompraPK> {

}
