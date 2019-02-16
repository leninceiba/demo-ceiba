package com.estacionamiento;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.repository.VehiculoParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;

@SpringBootApplication
public class EstacionamientoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EstacionamientoApplication.class, args);
	}	
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EstacionamientoApplication.class);
	}	

	@Bean
	public CommandLineRunner initServiciosParqueadero(ServicioParqueoRepository servicioParqueoRepository, VehiculoParqueoRepository vehiculoParqueoRepository) {
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
			
		};

	}
}
