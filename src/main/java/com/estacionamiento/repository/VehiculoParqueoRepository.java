package com.estacionamiento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento.entity.VehiculoParqueoEntity;

public interface VehiculoParqueoRepository extends JpaRepository<VehiculoParqueoEntity, Long> {

	public  VehiculoParqueoEntity findByPlacaVehiculo(String placaVehiculo);
	
	@Query("select f from VehiculoParqueoEntity f where f.placaVehiculo = ?1 and f.estado = ?2")
	public VehiculoParqueoEntity findByPlacaVehiculoByEstado(String placaVehiculo, String estado);
	
	public VehiculoParqueoEntity findById(long id);
	
	public List<VehiculoParqueoEntity> findAll();
}
