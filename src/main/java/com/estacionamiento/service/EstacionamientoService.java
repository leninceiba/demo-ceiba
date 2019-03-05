package com.estacionamiento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.entity.VehiculoParqueoEntity;
import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.repository.ServicioParqueoRepository;
import com.estacionamiento.repository.VehiculoParqueoRepository;
import com.estacionamiento.model.ServicioParqueo;

@Service
public class EstacionamientoService {
	
	@Autowired
	ServicioParqueoRepository servicioParqueoRepository;
	
	@Autowired
	VehiculoParqueoRepository vehiculoParqueoRepository;
	
	public VehiculoParqueoEntity findVehiculoParqueoById(long id){
		
		return vehiculoParqueoRepository.findById(id);
	}
	
	public VehiculoParqueoEntity findVehiculoParqueoByPlacaByEstado(String placaVehiculo, String estado){
		
		return vehiculoParqueoRepository.findByPlacaVehiculoByEstado(placaVehiculo, estado);
	}
	
	public ServicioParqueoEntity findServicioParqueoByCodigo(int codigo){
		
		return servicioParqueoRepository.findByCodigo(codigo);
	}
	
	public VehiculoParqueoEntity instanciarVehiculoParqueoEntity(VehiculoParqueo vehiculoParqueo){
		
		return new VehiculoParqueoEntity(vehiculoParqueo);
	}
	
	public void registrarEntradaVehiculoParqueo(VehiculoParqueo vehiculoParqueo, ServicioParqueo servicioParqueo){
		
		VehiculoParqueoEntity vehiculoParqueoEntity = this.instanciarVehiculoParqueoEntity(vehiculoParqueo);
		vehiculoParqueoRepository.save(vehiculoParqueoEntity);
		servicioParqueoRepository.descontarCupoDisponible(servicioParqueo.getId());
	}
	
	public void registrarSalidaVehiculoParqueo(VehiculoParqueoEntity vehiculoParqueoEntity){
		
		vehiculoParqueoRepository.save(vehiculoParqueoEntity);
		servicioParqueoRepository.aumentarCupoDisponible(vehiculoParqueoEntity.getServicioParqueoEntity().getId());
	}
	
	public List<VehiculoParqueoEntity> findAllVehiculosParqueados(){
		
		return vehiculoParqueoRepository.findAll();
	}
}
