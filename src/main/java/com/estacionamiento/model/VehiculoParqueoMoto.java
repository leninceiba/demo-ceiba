package com.estacionamiento.model;

import java.util.Calendar;

import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.entity.VehiculoParqueoEntity;

public class VehiculoParqueoMoto extends VehiculoParqueo {

	private static final long serialVersionUID = 1L;
	private String placa;
	private int cilindraje;	
	
	
	public VehiculoParqueoMoto() {
		super();
	}
    
	public VehiculoParqueoMoto(long id, String placaMoto, int cilindrajeMoto, Calendar fechaEntrada, Calendar fechaSalida, String estado, ServicioParqueo servicio) {
    	
    	 super(id,fechaEntrada,fechaSalida,estado,0,servicio);
    	 this.placa = placaMoto;
    	 this.cilindraje = cilindrajeMoto;
    }
	
	public VehiculoParqueoMoto(VehiculoParqueoEntity vehiculoParqueoEntity) {    	
    	 super(vehiculoParqueoEntity.getId(), vehiculoParqueoEntity.getFechaEntrada(), vehiculoParqueoEntity.getFechaSalida(), vehiculoParqueoEntity.getEstado(), vehiculoParqueoEntity.getValorServicio(), new ServicioParqueo(vehiculoParqueoEntity.getServicioParqueoEntity()));
    	 this.placa = vehiculoParqueoEntity.getPlacaVehiculo();
    	 this.cilindraje = vehiculoParqueoEntity.getCilindrajeMoto();
    }

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}	
	
	@Override
	public void calcularValorServicioParqueo(){
		
		setFechaSalida(Calendar.getInstance());
		TiempoServicio tiempoServicio = EstacionamientoUtil.calcularTiempoServicio(this.getFechaEntrada(), this.getFechaSalida());
		this.valorServicio = this.calcularValorTotalAPagar(tiempoServicio);
		this.aplicarRecargoPorCilindraje();
		long diasEnHoras = tiempoServicio.getDias() * EstacionamientoUtil.DIA_EN_HORAS;
		this.tiempoServicioHoras = tiempoServicio.getHoras() + diasEnHoras;
	}	
	
	public void aplicarRecargoPorCilindraje(){
		
		this.valorServicio = this.getCilindraje() > EstacionamientoUtil.RANGO_CILINDRAJE_APLICA_RECARGO ? (this.valorServicio + EstacionamientoUtil.RECARGO_CILINDRAJES_MAYORES_A_500) : this.valorServicio;
	}

}
