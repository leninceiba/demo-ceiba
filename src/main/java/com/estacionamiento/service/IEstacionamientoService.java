package com.estacionamiento.service;

import java.util.List;

import com.estacionamiento.entity.VehiculoParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;

public interface IEstacionamientoService {
	
	public VehiculoParqueoEntity registrarEntradaEstacionamiento(PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException;
	
	public VehiculoParqueo registrarSalidaEstacionamiento(long idFactura) throws EstacionamientoException;
	
	public List<VehiculoParqueoEntity> consultarVehiculos() throws EstacionamientoException;
		

}
