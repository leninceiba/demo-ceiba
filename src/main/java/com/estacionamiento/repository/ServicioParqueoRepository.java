package com.estacionamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.estacionamiento.entity.ServicioParqueoEntity;

public interface ServicioParqueoRepository extends JpaRepository<ServicioParqueoEntity, Long>{

	public ServicioParqueoEntity findByCodigo(int codigo);
	
	@Modifying
	@Query("update ServicioParqueoEntity s set s.cupoDisponible = (s.cupoDisponible-1) where s.id = ?1")
	int descontarCupoDisponible(long id);
	
	@Modifying
	@Query("update ServicioParqueoEntity s set s.cupoDisponible = (s.cupoDisponible+1) where s.id = ?1 ")
	int aumentarCupoDisponible(long id);
}
