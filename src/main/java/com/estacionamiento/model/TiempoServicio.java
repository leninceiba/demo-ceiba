package com.estacionamiento.model;

public class TiempoServicio {

	private long dias;
	private long horas;
	
	public TiempoServicio(long horas,long dias) {
		this.horas = horas;
		this.dias = dias;
	}

	public long getDias() {
		return dias;
	}

	public void setDias(long dias) {
		this.dias = dias;
	}

	public long getHoras() {
		return horas;
	}

	public void setHoras(long horas) {
		this.horas = horas;
	}

}
