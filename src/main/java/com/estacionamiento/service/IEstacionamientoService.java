package com.estacionamiento.service;

import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.ServicioParqueo;

public interface IEstacionamientoService {
	
	public FacturaParqueoEntity registrarEntradaEstacionamiento(ServicioParqueo servicioParqueo) throws EstacionamientoException;
		

}
