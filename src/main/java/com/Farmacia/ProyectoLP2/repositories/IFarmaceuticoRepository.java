package com.Farmacia.ProyectoLP2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
	
	@Modifying
	@Query("UPDATE Farmaceutico f SET f.estado.id = 1 WHERE f.idFarmaceutico = :id")
	void activarMedicamento(@Param("id") Integer id);

	@Modifying
	@Query("UPDATE Farmaceutico f SET f.estado.id = 2 WHERE f.idFarmaceutico = :id")
	void desactivarMedicamento(@Param("id") Integer id);

}
