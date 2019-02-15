package com.estacionamiento.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.estacionamiento.model.TiempoServicio;
import com.estacionamiento.service.impl.EstacionamientoServiceImpl;

public final class EstacionamientoUtil {
	
	public static final String SIN_CUPO = "No hay cupos disponibles.";
	public static final String EXISTE_VEHICULO = "Ya existe un vehículo parqueado con esa placa.";
	public static final String PLACA_NO_AUTORIZADA = "Las placas que inician por la letra A solo pueden entrar al estacionamiento los días domingos y lunes.";
	public static final String ESTADO_PENDIENTE = "PENDIENTE";
	public static final String ESTADO_PAGADO = "PAGADO";
	public static final String ERROR_CREANDO_FACTURA = "Error intentando crear la factura del parqueo.";
	public static final String ERROR_METODO_REGISTRO_FACTURA = "Error en el método registrarEntradaEstacionamiento: ";
	public static final String ERROR_METODO_COMPROBAR_DISPONIBILIDAD = "Error en el método comprobarDisponibilidadParqueo: ";
	public static final String PLACA_PRUEBA = "DNP142";
	public static final String PLACA_EMPIEZA_CON_A = "ANP142";
	public static final Calendar FECHA_ENTRADA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","12-03-2019 12:00:00");
	public static final Calendar FECHA_SALIDA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","13-03-2019 15:00:00");
	public static final int UN_SEGUNDO_EN_MILISEGUNDOS = 1000;
	public static final int UNA_HORA_EN_SEGUNDOS = 3600;
	public static final int UNA_HORA_EN_MILISEGUNDOS = 3600000;
	public static final int UN_MINUTO_EN_MILISEGUNDOS = 60000;
	public static final int RANGO_COBRO_POR_HORAS = 9;
	public static final int RANGO_CILINDRAJE_APLICA_RECARGO = 500;
	public static final int RECARGO_CILINDRAJES_MAYORES_A_500 = 2000;
	public static final int DIA_EN_HORAS = 24;
	public static final int COBRAR_UN_DIA = 1;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstacionamientoServiceImpl.class);
	
	private EstacionamientoUtil() {
		super();
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
			
			LOGGER.error("Error en el método getFechaCalendar: ",e);
		}
		fechaCalendar.setTime(date);
		
		return fechaCalendar;
	}
	
	public static TiempoServicio calcularTiempoServicio(Calendar fechaEntrada, Calendar fechaSalida) {
		
		long totalHorasServicio = ((fechaSalida.getTimeInMillis() - fechaEntrada.getTimeInMillis())/UN_SEGUNDO_EN_MILISEGUNDOS)/UNA_HORA_EN_SEGUNDOS;
		long diasServicio = totalHorasServicio/24;
		long horasServicio = totalHorasServicio % 24;
		
		return new TiempoServicio(horasServicio, diasServicio);
	}	
}
