package com.estacionamiento.model;

import java.util.Calendar;

public class FacturaParqueo {

	private long id;
	private Calendar fechaEntrada;
	protected Calendar fechaSalida;
	private String estado;
	protected long valorServicio;
	protected long tiempoServicioHoras;
	private ServicioParqueo servicioParqueo;
	private String error;
	
	public FacturaParqueo() {
		super();
	}

	public FacturaParqueo(long id, Calendar fechaEntrada, Calendar fechaSalida, String estado, long valorServicio,
			ServicioParqueo servicioParqueo) {
		super();
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.estado = estado;
		this.valorServicio = valorServicio;
		this.servicioParqueo = servicioParqueo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Calendar fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Calendar getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Calendar fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public long getValorServicio() {
		return valorServicio;
	}

	public void setValorServicio(long valorServicio) {
		this.valorServicio = valorServicio;
	}

	public long getTiempoServicioHoras() {
		return tiempoServicioHoras;
	}

	public void setTiempoServicioHoras(long tiempoServicioHoras) {
		this.tiempoServicioHoras = tiempoServicioHoras;
	}

	public ServicioParqueo getServicioParqueo() {
		return servicioParqueo;
	}

	public void setServicioParqueo(ServicioParqueo servicioParqueo) {
		this.servicioParqueo = servicioParqueo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}	
}
