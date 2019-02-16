package com.estacionamiento.model;

import java.io.Serializable;
import java.util.Calendar;

public class PeticionServicioParqueo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String placaVehiculo;
	private String cilindrajeMoto;
	private int tipoVehiculo;
	private String error;	
	private Calendar fecha = Calendar.getInstance();
	
	public PeticionServicioParqueo(){
		
	}
	
	public PeticionServicioParqueo(String placaVehiculo, String cilindrajeMoto, int tipoVehiculo, Calendar fecha) {
		super();
		this.placaVehiculo = placaVehiculo;
		this.cilindrajeMoto = cilindrajeMoto;
		this.tipoVehiculo = tipoVehiculo;
		this.fecha = fecha;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public String getCilindrajeMoto() {
		return cilindrajeMoto;
	}

	public void setCilindrajeMoto(String cilindrajeMoto) {
		this.cilindrajeMoto = cilindrajeMoto;
	}

	public int getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(int tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}
}
