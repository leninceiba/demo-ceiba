package com.estacionamiento.service;

import java.util.List;

import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.FacturaParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;

public interface IEstacionamientoService {
	
	public FacturaParqueoEntity registrarEntradaEstacionamiento(PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException;
	
	public FacturaParqueo registrarSalidaEstacionamiento(long idFactura) throws EstacionamientoException;
	
	public List<FacturaParqueoEntity> consultarFacturas() throws EstacionamientoException;
		

}
