package com.estacionamiento.model;

import java.util.Calendar;

import com.estacionamiento.entity.FacturaParqueoEntity;

public class FacturaParqueoCarro extends FacturaParqueo{
	
	private String placa;

	public FacturaParqueoCarro() {
		super();
	}

	public FacturaParqueoCarro(long id, String placaCarro, Calendar fechaEntrada, Calendar fechaSalida, String estado, long valorServicio,
			ServicioParqueo servicioParqueo) {
		super(id, fechaEntrada, fechaSalida, estado, valorServicio, servicioParqueo);
		this.placa = placaCarro;
	}

	public FacturaParqueoCarro(FacturaParqueoEntity facturaParqueoEntity) {
		super(facturaParqueoEntity.getId(), facturaParqueoEntity.getFechaEntrada(), facturaParqueoEntity.getFechaSalida(), facturaParqueoEntity.getEstado(), facturaParqueoEntity.getValorServicio(), new ServicioParqueo(facturaParqueoEntity.getServicioParqueo()));
		this.placa = facturaParqueoEntity.getPlacaVehiculo();
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

}
