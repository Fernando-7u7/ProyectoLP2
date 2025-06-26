package com.Farmacia.ProyectoLP2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Farmacia.ProyectoLP2.model.Farmaceutico;


public interface IFarmaceuticoRepository extends JpaRepository<Farmaceutico, Integer> {

	List<Farmaceutico> findAllByOrderByIdFarmaceuticoDesc();

	@Query("""
			SELECT f FROM Farmaceutico f
			WHERE
			(:tipoDoc is null or f.tipoDoc = :tipoDoc)
			ORDER BY
			    f.idFarmaceutico DESC
			""")
	List<Farmaceutico> findAllWithFilters(@Param("tipoDoc") String tipoDoc);

}
