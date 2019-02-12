package com.estacionamiento.builders;

import java.util.Calendar;

import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.model.FacturaParqueoCarro;
import com.estacionamiento.model.FacturaParqueoMoto;
import com.estacionamiento.model.ServicioParqueo;

public class FacturaParqueoBuild {
	
	private static final Calendar FECHA_ENTRADA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","12-03-2019 10:00:00");
	private static final Calendar FECHA_SALIDA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","18-03-2019 13:10:00");	

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
		this.fechaEntrada = FECHA_ENTRADA;
		this.fechaSalida = FECHA_SALIDA;
		this.estado = "PENDIENTE";
		this.placaVehiculo = "DNP142";
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
