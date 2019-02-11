package com.estacionamiento.commons.util;

import java.util.Calendar;

public final class EstacionamientoUtil {
	
	private static EstacionamientoUtil estacionamientoUtil; 
	public static final String SIN_CUPO = "No hay cupos disponibles.";
	public static final String PLACA_NO_AUTORIZADA = "Las placas que inician por la letra A no estan autorizadas para entrar al estacionamiento los días domingos y lunes.";
	public static final String ESTADO_PENDIENTE = "PENDIENTE";
	public static final String ESTADO_PAGADO = "PAGADO";
	public static final String ERROR_CREANDO_FACTURA = "Error intentando crear la factura del parqueo.";
	public static final String PLACA_PRUEBA = "dnp142";
	

	public static EstacionamientoUtil getInstance(){
		
		if(estacionamientoUtil == null){
			
			estacionamientoUtil = new EstacionamientoUtil();
		}
		
		return estacionamientoUtil;
	}

	public static boolean esDomingoOLunes(Calendar fechaHoy) {
		
		boolean esDomingoOLunes = false;
		esDomingoOLunes = (fechaHoy.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || fechaHoy.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY);
		
		return esDomingoOLunes;
	}

	public static boolean placaVehiculoIniciaPorA(String placaVehiculo) {
		
		return placaVehiculo.toUpperCase().indexOf('A') == 0;
	}	
}
