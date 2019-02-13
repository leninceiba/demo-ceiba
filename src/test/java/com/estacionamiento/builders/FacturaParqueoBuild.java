package com.estacionamiento.builders;

import java.util.Calendar;

import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.model.FacturaParqueoCarro;
import com.estacionamiento.model.FacturaParqueoMoto;
import com.estacionamiento.model.ServicioParqueo;

public class FacturaParqueoBuild {

	private long id;
	private Calendar fechaEntrada;
	protected Calendar fechaSalida;
	private String estado;
	protected long valorServicio;
	protected long tiempoServicioHoras;
	private ServicioParqueo servicioParqueo;
	private String placaVehiculo;
	private int cilindrajeMoto;
	
	public FacturaParqueoBuild() {
		this.fechaEntrada = EstacionamientoUtil.FECHA_ENTRADA;
		this.fechaSalida = EstacionamientoUtil.FECHA_SALIDA;
		this.estado = EstacionamientoUtil.ESTADO_PENDIENTE;
		this.placaVehiculo = "XHJ142";
		this.servicioParqueo = new ServicioParqueoBuild().withCodigo(1).withDescripcion("carro").withCupoMaximo(20).withTarifaHora(1000).withTarifaDia(8000).build();
	}

	public FacturaParqueoBuild withId(long id) {
		this.id = id;
		return this;
	}

	public FacturaParqueoBuild withFechaEntrada(Calendar fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
		return this;
	}

	public FacturaParqueoBuild withFechaSalida(Calendar fechaSalida) {
		this.fechaSalida = fechaSalida;
		return this;
	}

	public FacturaParqueoBuild withEstado(String estado) {
		this.estado = estado;
		return this;
	}

	public FacturaParqueoBuild withServicioParqueo(ServicioParqueo servicioParqueo) {
		this.servicioParqueo = servicioParqueo;
		return this;
	}

	public FacturaParqueoBuild withPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
		return this;
	}	
	
	public FacturaParqueoBuild withCilindrajeMoto(int cilindrajeMoto){
		this.cilindrajeMoto = cilindrajeMoto;
		return this;
	}
	
	public FacturaParqueoCarro buildFacturaParqueoCarro(){
		return new FacturaParqueoCarro(id, placaVehiculo, fechaEntrada, fechaSalida, estado, valorServicio, servicioParqueo);
	}
	
	public FacturaParqueoMoto buildFacturaParqueoMoto(){
		return new FacturaParqueoMoto(id, placaVehiculo, cilindrajeMoto, fechaEntrada, fechaSalida, estado, servicioParqueo);
	}
	
	public static FacturaParqueoBuild aFacturaParqueoBuild(){
		return new FacturaParqueoBuild();		
	}
	
}
