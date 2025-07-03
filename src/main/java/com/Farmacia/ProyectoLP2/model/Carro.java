package com.Farmacia.ProyectoLP2.model;

import java.util.HashMap;
import java.util.Map;

import com.Farmacia.ProyectoLP2.util.Alert;

public class Carro {
	private Map<String, DetalleCompra> items = new HashMap<>();

	public String agregarProducto(Medicamento medicamento, int cantidad) {
	    String id = medicamento.getIdMedicamento();
	    int stock = medicamento.getStockActual(); 

	    if (items.containsKey(id)) {
	        DetalleCompra detalle = items.get(id);
	        int nuevaCantidad = detalle.getCantidad() + cantidad;

	        if (nuevaCantidad > stock) {

	            return Alert.sweetAlertError("No hay suficiente stock. Stock disponible: " + stock);
	        } else {
	            detalle.setCantidad(nuevaCantidad);
	        }
	    } else {
	        if (cantidad > stock) {
	            return Alert.sweetAlertError("No hay suficiente stock. Stock disponible: " + stock);
	        }
	        DetalleCompra detalle = new DetalleCompra();
	        detalle.setMedicamento(medicamento);
	        detalle.setCantidad(cantidad);
	        detalle.setPrecio(medicamento.getPrecio());
	        items.put(id, detalle);
	    }
	    return null; 
	}
	
	public String aumentarCantidad(String idMedicamento) {
	    DetalleCompra detalle = items.get(idMedicamento);
	    if (detalle != null) {
	        int nuevaCantidad = detalle.getCantidad() + 1;
	        if (nuevaCantidad > detalle.getMedicamento().getStockActual()) {
	            return Alert.sweetAlertError("Stock insuficiente. Stock máximo: " + detalle.getMedicamento().getStockActual());
	        }
	        detalle.setCantidad(nuevaCantidad);
	    }
	    return null;
	}

	public String disminuirCantidad(String idMedicamento) {
	    DetalleCompra detalle = items.get(idMedicamento);
	    if (detalle != null) {
	        int nuevaCantidad = detalle.getCantidad() - 1;
	        if (nuevaCantidad <= 0) {
	            items.remove(idMedicamento);
	        } else {
	            detalle.setCantidad(nuevaCantidad);
	        }
	    }
	    return null;
	}


	public void eliminarProducto(String idMedicamento) {
		items.remove(idMedicamento);
	}

	public Map<String, DetalleCompra> getItems() {
		return items;
	}

	public int getCantidadTotalUnidades() {
	    return items.values().stream()
	        .mapToInt(DetalleCompra::getCantidad)
	        .sum();
	}
	
	public void limpiar() {
		items.clear();
	}
}
