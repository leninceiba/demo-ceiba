package com.estacionamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento.entity.FacturaParqueoEntity;

public interface FacturaParqueoRepository extends JpaRepository<FacturaParqueoEntity, Long> {

	public  FacturaParqueoEntity findByPlacaVehiculo(String placaVehiculo);
	
	public  FacturaParqueoEntity findById(long id);
	
	public List<FacturaParqueoEntity> findAllByEstadoOrderByFechaEntradaDesc(String estado);
	
	@Modifying
	@Query("update FacturaParqueoEntity c set c.estado = ?1 where c.id = ?2")
	int actualizarEstadoFacturaParqueo(String estado, long id);	
}
