package com.Farmacia.ProyectoLP2.model;

import java.util.HashMap;
import java.util.Map;

public class Carro {
	private Map<String, DetalleCompra> items = new HashMap<>();

	public void agregarProducto(Medicamento medicamento, int cantidad) {
		String id = medicamento.getIdMedicamento();
		if (items.containsKey(id)) {
			DetalleCompra detalle = items.get(id);
			detalle.setCantidad(detalle.getCantidad() + cantidad);
		} else {
			DetalleCompra detalle = new DetalleCompra();
			detalle.setMedicamento(medicamento);
			detalle.setCantidad(cantidad);
			detalle.setPrecio(medicamento.getPrecio());
			items.put(id, detalle);
		}
	}

	public void eliminarProducto(String idMedicamento) {
		items.remove(idMedicamento);
	}

	public Map<String, DetalleCompra> getItems() {
		return items;
	}

	public void limpiar() {
		items.clear();
	}
}
