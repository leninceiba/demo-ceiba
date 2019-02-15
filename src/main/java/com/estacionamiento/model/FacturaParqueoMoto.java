package com.estacionamiento.model;

import java.util.Calendar;

import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.entity.FacturaParqueoEntity;

public class FacturaParqueoMoto extends FacturaParqueo {
	
	private String placa;
	private int cilindraje;	
	
	
	public FacturaParqueoMoto() {
		super();
	}
    
	public FacturaParqueoMoto(long id, String placaMoto, int cilindrajeMoto, Calendar fechaEntrada, Calendar fechaSalida, String estado, ServicioParqueo servicio) {
    	
    	 super(id,fechaEntrada,fechaSalida,estado,0,servicio);
    	 this.placa = placaMoto;
    	 this.cilindraje = cilindrajeMoto;
    }
	
	public FacturaParqueoMoto(FacturaParqueoEntity facturaParqueoEntity) {    	
    	 super(facturaParqueoEntity.getId(), facturaParqueoEntity.getFechaEntrada(), facturaParqueoEntity.getFechaSalida(), facturaParqueoEntity.getEstado(), facturaParqueoEntity.getValorServicio(), new ServicioParqueo(facturaParqueoEntity.getServicioParqueo()));
    	 this.placa = facturaParqueoEntity.getPlacaVehiculo();
    	 this.cilindraje = facturaParqueoEntity.getCilindrajeMoto();
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
