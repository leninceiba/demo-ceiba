package com.estacionamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamiento.entity.FacturaParqueoEntity;

public interface FacturaParqueoRepository extends JpaRepository<FacturaParqueoEntity, Long> {

	public  FacturaParqueoEntity findByPlacaVehiculo(String placaVehiculo);
	
	@Query("select f from FacturaParqueoEntity f where f.placaVehiculo = ?1 and f.estado = ?2")
	public FacturaParqueoEntity findByPlacaVehiculoByEstado(String placaVehiculo, String estado);
	
	public FacturaParqueoEntity findById(long id);
	
	public List<FacturaParqueoEntity> findAllOrderByFechaEntradaDesc();
	
	@Transactional
	@Modifying
	@Query("update FacturaParqueoEntity c set c.estado = ?1 where c.id = ?2")
	int actualizarEstadoFacturaParqueo(String estado, long id);	
}
