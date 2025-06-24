package com.Farmacia.ProyectoLP2.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.Farmacia.ProyectoLP2.model.Medicamento;


public interface IMedicamentoRepository extends JpaRepository<Medicamento, String> {
	List<Medicamento> findAllByOrderByIdMedicamentoDesc();
	
	@Modifying
	@Query("UPDATE Medicamento m SET m.estado.id = 1 WHERE m.idMedicamento = :id")
	void activarMedicamento(@Param("id") String id);

	@Modifying
	@Query("UPDATE Medicamento m SET m.estado.id = 2 WHERE m.idMedicamento = :id")
	void desactivarMedicamento(@Param("id") String id);
	
	@Modifying
    @Query(value = "CALL SP_CREATE_MEDICINE(:imagen, :nombre, :precio, :stockActual,:fechaVencimiento, :descripcion, :idProveedor, :preescripcion, :idCategoria)", nativeQuery = true)
    void createMedicine(
        @Param("imagen") byte[] imagen,
        @Param("nombre") String nombre,
        @Param("precio") Double precio,
        @Param("stockActual") Integer stockActual,
        @Param("fechaVencimiento") LocalDate fechaVencimiento,
        @Param("descripcion") String descripcion,
        @Param("idProveedor") Integer idProveedor,
        @Param("preescripcion") String preescripcion,
        @Param("idCategoria") Integer idCategoria
    );
	
	@Modifying
	@Query(value = "CALL SP_UPDATE_MEDICINE(:idMedicamento, :imagen, :nombre, :precio, :stockActual, :fechaVencimiento, :descripcion, :idProveedor, :preescripcion, :idCategoria)", nativeQuery = true)
	void updateMedicine(
	    @Param("idMedicamento") String idMedicamento,
	    @Param("imagen") byte[] imagen,
	    @Param("nombre") String nombre,
	    @Param("precio") Double precio,
	    @Param("stockActual") Integer stockActual,
	    @Param("fechaVencimiento") LocalDate fechaVencimiento,
	    @Param("descripcion") String descripcion,
	    @Param("idProveedor") Integer idProveedor,
	    @Param("preescripcion") String preescripcion,
	    @Param("idCategoria") Integer idCategoria
	);

}
