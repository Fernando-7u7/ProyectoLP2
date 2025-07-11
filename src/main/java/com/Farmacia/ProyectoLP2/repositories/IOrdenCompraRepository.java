package com.Farmacia.ProyectoLP2.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Farmacia.ProyectoLP2.dto.IMonthlySale;
import com.Farmacia.ProyectoLP2.model.OrdenCompra;
import com.Farmacia.ProyectoLP2.model.Rol;

public interface IOrdenCompraRepository extends JpaRepository<OrdenCompra, Integer> {
	List<OrdenCompra> findAllByOrderByIdOrdenDesc();

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

	@Query("""
			    SELECT o FROM OrdenCompra o
			    WHERE (:idRol IS NULL OR o.usuario.rol.idRol = :idRol)
			    ORDER BY o.idOrden DESC
			""")
	List<OrdenCompra> findByRolAdmin(@Param("idRol") Integer idRol);

	@Query("""
			SELECT o FROM OrdenCompra o
			WHERE
			(:rol IS NULL OR o.usuario.rol = :rol)
			ORDER BY
			o.idOrden DESC
			""")
	List<OrdenCompra> findByRol(Rol rol);

	@Query("""
			SELECT o FROM OrdenCompra o
			WHERE
			o.fecha BETWEEN :fechaIni AND :fechaFin
			AND
			(:rol IS NULL OR o.usuario.rol = :rol)
			ORDER BY
			o.idOrden DESC
			""")
	List<OrdenCompra> findByFechaAndRol(@Param("fechaIni") LocalDate fechaIni, @Param("fechaFin") LocalDate fechaFin,
			Rol rol);

	@Query("""
			    SELECT o FROM OrdenCompra o
			    WHERE o.usuario.idUsuario = :idUsuario
			    AND o.fecha BETWEEN :fechaIni AND :fechaFin
			    ORDER BY o.idOrden DESC
			""")
	List<OrdenCompra> findByUsuarioAndFecha(@Param("idUsuario") Integer idUsuario,
			@Param("fechaIni") LocalDate fechaIni, @Param("fechaFin") LocalDate fechaFin);

	@Query("SELECT o FROM OrdenCompra o WHERE o.usuario.id = :id ORDER BY o.idOrden DESC")
	List<OrdenCompra> findByUsuarioId(@Param("id") Integer id);

}