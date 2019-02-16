package com.estacionamiento.builders;

import java.util.Calendar;

import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.model.VehiculoParqueoCarro;
import com.estacionamiento.model.VehiculoParqueoMoto;
import com.estacionamiento.model.ServicioParqueo;

public class VehiculoParqueoBuild {

	private long id;
	private Calendar fechaEntrada;
	protected Calendar fechaSalida;
	private String estado;
	protected long valorServicio;
	protected long tiempoServicioHoras;
	private ServicioParqueo servicioParqueo;
	private String placaVehiculo;
	private int cilindrajeMoto;
	public Calendar FECHA_ENTRADA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","12-03-2019 12:00:00");
	public Calendar FECHA_SALIDA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","13-03-2019 15:00:00");		
	
	public VehiculoParqueoBuild() {
		this.fechaEntrada = FECHA_ENTRADA;
		this.fechaSalida = FECHA_SALIDA;
		this.estado = EstacionamientoUtil.ESTADO_PENDIENTE;
		this.placaVehiculo = "XHJ142";
		this.servicioParqueo = new ServicioParqueoBuild().withCodigo(1).withDescripcion("carro").withCupoMaximo(20).withTarifaHora(1000).withTarifaDia(8000).build();
	}

	public VehiculoParqueoBuild withId(long id) {
		this.id = id;
		return this;
	}

	public VehiculoParqueoBuild withFechaEntrada(Calendar fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
		return this;
	}

	public VehiculoParqueoBuild withFechaSalida(Calendar fechaSalida) {
		this.fechaSalida = fechaSalida;
		return this;
	}

	public VehiculoParqueoBuild withEstado(String estado) {
		this.estado = estado;
		return this;
	}

	public VehiculoParqueoBuild withServicioParqueo(ServicioParqueo servicioParqueo) {
		this.servicioParqueo = servicioParqueo;
		return this;
	}

	public VehiculoParqueoBuild withPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
		return this;
	}	
	
	public VehiculoParqueoBuild withCilindrajeMoto(int cilindrajeMoto){
		this.cilindrajeMoto = cilindrajeMoto;
		return this;
	}
	
	public VehiculoParqueoCarro buildVehiculoParqueoCarro(){
		return new VehiculoParqueoCarro(id, placaVehiculo, fechaEntrada, fechaSalida, estado, valorServicio, servicioParqueo);
	}
	
	public VehiculoParqueoMoto buildVehiculoParqueoMoto(){
		return new VehiculoParqueoMoto(id, placaVehiculo, cilindrajeMoto, fechaEntrada, fechaSalida, estado, servicioParqueo);
	}
	
	public static VehiculoParqueoBuild aVehiculoParqueoBuild(){
		return new VehiculoParqueoBuild();		
	}
	
}
