package com.estacionamiento.model;

import java.util.Calendar;

import com.estacionamiento.entity.VehiculoParqueoEntity;

public class VehiculoParqueoCarro extends VehiculoParqueo{
	
	private String placa;

	public VehiculoParqueoCarro() {
		super();
	}

	public VehiculoParqueoCarro(long id, String placaCarro, Calendar fechaEntrada, Calendar fechaSalida, String estado, long valorServicio,
			ServicioParqueo servicioParqueo) {
		super(id, fechaEntrada, fechaSalida, estado, valorServicio, servicioParqueo);
		this.placa = placaCarro;
	}

	public VehiculoParqueoCarro(VehiculoParqueoEntity vehiculoParqueoEntity) {
		super(vehiculoParqueoEntity.getId(), vehiculoParqueoEntity.getFechaEntrada(), vehiculoParqueoEntity.getFechaSalida(), vehiculoParqueoEntity.getEstado(), vehiculoParqueoEntity.getValorServicio(), new ServicioParqueo(vehiculoParqueoEntity.getServicioParqueo()));
		this.placa = vehiculoParqueoEntity.getPlacaVehiculo();
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}
