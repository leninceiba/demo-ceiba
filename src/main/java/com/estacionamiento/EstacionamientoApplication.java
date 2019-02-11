package com.estacionamiento;

import java.util.Calendar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.model.FacturaParqueoCarro;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.repository.FacturaParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;

@SpringBootApplication
public class EstacionamientoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstacionamientoApplication.class, args);
	}	

	@Bean
	public CommandLineRunner initServiciosParqueadero(ServicioParqueoRepository servicioParqueoRepository, FacturaParqueoRepository facturaParqueoRepository) {
		return args -> {
			ServicioParqueoEntity servicioParqueoCarro = servicioParqueoRepository.findByCodigo(1);
			ServicioParqueoEntity servicioParqueoMoto = servicioParqueoRepository.findByCodigo(2);
			if(servicioParqueoCarro == null) {
				servicioParqueoCarro = new ServicioParqueoEntity(1,"carro", 20, 20, 1000, 8000);
				servicioParqueoRepository.save(servicioParqueoCarro);
			}
			
			if(servicioParqueoMoto == null) {
				servicioParqueoMoto = new ServicioParqueoEntity(2,"moto", 10, 10, 500, 4000);
				servicioParqueoRepository.save(servicioParqueoMoto);
			}
			
			FacturaParqueoCarro facturaParqueo = new FacturaParqueoCarro();
			facturaParqueo.setFechaEntrada(Calendar.getInstance());
			Calendar fechaSalida = Calendar.getInstance();
			fechaSalida.set(Calendar.YEAR, 2019);
			fechaSalida.set(Calendar.MONTH, Calendar.FEBRUARY);
			fechaSalida.set(Calendar.DAY_OF_MONTH, 11);
			fechaSalida.set(Calendar.HOUR_OF_DAY, 11);
			facturaParqueo.setEstado(EstacionamientoUtil.ESTADO_PENDIENTE);
			facturaParqueo.setValorServicio(0);
			facturaParqueo.setPlaca(EstacionamientoUtil.PLACA_PRUEBA);
			ServicioParqueo servicioParqueo = new ServicioParqueo(servicioParqueoCarro);
			facturaParqueo.setServicioParqueo(servicioParqueo);
			
			FacturaParqueoEntity facturaParqueoEntity = new FacturaParqueoEntity(facturaParqueo);
			facturaParqueoRepository.save(facturaParqueoEntity);
			servicioParqueoRepository.descontarCupoDisponible(servicioParqueo.getId());
			
		};

	}
}
