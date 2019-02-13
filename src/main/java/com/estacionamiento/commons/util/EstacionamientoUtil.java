package com.estacionamiento.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class EstacionamientoUtil {
	
	private static EstacionamientoUtil estacionamientoUtil; 
	public static final String SIN_CUPO = "No hay cupos disponibles.";
	public static final String EXISTE_VEHICULO = "Ya existe un veh�culo parqueado con esa placa.";
	public static final String PLACA_NO_AUTORIZADA = "Las placas que inician por la letra A solo pueden entrar al estacionamiento los d�as domingos y lunes.";
	public static final String ESTADO_PENDIENTE = "PENDIENTE";
	public static final String ESTADO_PAGADO = "PAGADO";
	public static final String ERROR_CREANDO_FACTURA = "Error intentando crear la factura del parqueo.";
	public static final String ERROR_METODO_REGISTRO_FACTURA = "Error en el m�todo registrarEntradaEstacionamiento: ";
	public static final String ERROR_METODO_COMPROBAR_DISPONIBILIDAD = "Error en el m�todo comprobarDisponibilidadParqueo: ";
	public static final String PLACA_PRUEBA = "DNP142";
	public static final String PLACA_EMPIEZA_CON_A = "ANP142";
	public static final Calendar FECHA_ENTRADA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","12-03-2019 10:00:00");
	public static final Calendar FECHA_SALIDA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","18-03-2019 13:10:00");		
	

	
	private EstacionamientoUtil() {
		super();
	}

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
	
	public static Calendar getFechaCalendar(String formato , String fecha) {
		
		Date date = new Date();
		Calendar fechaCalendar = Calendar.getInstance();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(formato);			
			date = sdf.parse(fecha);
			
		} catch (Exception e) {
		}
		fechaCalendar.setTime(date);
		
		return fechaCalendar;
	}	
}
