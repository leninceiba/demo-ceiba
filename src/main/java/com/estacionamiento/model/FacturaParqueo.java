package com.estacionamiento.model;

import java.util.Calendar;

import com.estacionamiento.commons.util.EstacionamientoUtil;

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
	
	public void calcularValorServicioParqueo(){
		
		setFechaSalida(Calendar.getInstance());
		TiempoServicio tiempoServicio = EstacionamientoUtil.calcularTiempoServicio(this.getFechaEntrada(), this.getFechaSalida());
		this.valorServicio = this.calcularValorTotalAPagar(tiempoServicio);
		long diasEnHoras = tiempoServicio.getDias() * EstacionamientoUtil.DIA_EN_HORAS;
		this.tiempoServicioHoras = tiempoServicio.getHoras() + diasEnHoras;
	}
	
	public long calcularValorTotalAPagar(TiempoServicio tiempoServicio){
		
		long valorPorDias = tiempoServicio.getDias() > 0 ? tiempoServicio.getDias() * this.getServicioParqueo().getTarifaDia() : 0;
		long valorPorHoras = tiempoServicio.getHoras() > 0 ? tiempoServicio.getHoras() * this.getServicioParqueo().getTarifaHora() : 0;
		
		if (EstacionamientoUtil.RANGO_COBRO_POR_HORAS <= tiempoServicio.getHoras()) {
			
			tiempoServicio.setDias(tiempoServicio.getDias() + 1);
			tiempoServicio.setHoras(0);
			valorPorHoras = tiempoServicio.getHoras() * this.getServicioParqueo().getTarifaHora();
			valorPorDias = tiempoServicio.getDias() * this.getServicioParqueo().getTarifaHora();
		}
		
		return valorPorHoras + valorPorDias;
	}
}
