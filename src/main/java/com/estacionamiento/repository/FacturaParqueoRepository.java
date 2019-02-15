package com.estacionamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento.entity.FacturaParqueoEntity;

public interface FacturaParqueoRepository extends JpaRepository<FacturaParqueoEntity, Long> {

	public  FacturaParqueoEntity findByPlacaVehiculo(String placaVehiculo);
	
	@Query("select f from FacturaParqueoEntity f where f.placaVehiculo = ?1 and f.estado = ?2")
	public FacturaParqueoEntity findByPlacaVehiculoByEstado(String placaVehiculo, String estado);
	
	public FacturaParqueoEntity findById(long id);
	
	public List<FacturaParqueoEntity> findAll();
}
