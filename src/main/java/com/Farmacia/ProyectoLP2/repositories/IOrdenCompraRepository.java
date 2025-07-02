package com.Farmacia.ProyectoLP2.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Farmacia.ProyectoLP2.dto.IMonthlySale;
import com.Farmacia.ProyectoLP2.model.OrdenCompra;

public interface IOrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
	@Modifying
	@Query(value = """
			    SELECT DATE_FORMAT(o.fecha, '%Y-%m') AS mes,
			           SUM(d.cantidad * d.precio) AS totalVendido
			    FROM OrdenCompra o
			    JOIN o.detalles d
			    WHERE o.fecha >= :fechaLimite
			    GROUP BY DATE_FORMAT(o.fecha, '%Y-%m')
			    ORDER BY mes
			""")
	List<IMonthlySale> getSalesForMonthlys(@Param("fechaLimite") LocalDate fechaLimite);
}
