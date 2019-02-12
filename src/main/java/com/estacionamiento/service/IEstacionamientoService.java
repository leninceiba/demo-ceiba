package com.estacionamiento.service;

import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.PeticionServicioParqueo;

public interface IEstacionamientoService {
	
	public FacturaParqueoEntity registrarEntradaEstacionamiento(PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException;
		

}
