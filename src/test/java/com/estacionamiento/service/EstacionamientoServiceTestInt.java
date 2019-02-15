package com.estacionamiento.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamiento.builders.ServicioParqueoBuild;
import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.controller.EstacionamientoController;
import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.repository.FacturaParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;
import com.estacionamiento.service.impl.EstacionamientoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EstacionamientoServiceTestInt {
	
	@Autowired
	ServicioParqueoRepository servicioParqueoRepository;
	
	@Autowired
	FacturaParqueoRepository facturaParqueoRepository;
	
	@Autowired
	EstacionamientoServiceImpl estacionamientoServiceImpl;
	
	@Autowired
	EstacionamientoController estacionamientoController;		
	
	@Before
	public void crearServicios(){

		ServicioParqueo servicioParqueoCarro = new ServicioParqueoBuild()
				.withCodigo(1)
				.withCupoMaximo(20)
				.withCupoDisponible(20)
				.withDescripcion("carro")
				.withTarifaHora(1000)
				.withTarifaDia(8000)
				.build();
		ServicioParqueoEntity servicioParqueoEntityCarro = new ServicioParqueoEntity(servicioParqueoCarro);	
		
		ServicioParqueo servicioParqueoMoto = new ServicioParqueoBuild()
				.withCodigo(2)
				.withCupoMaximo(10)
				.withCupoDisponible(10)
				.withDescripcion("moto")
				.withTarifaHora(500)
				.withTarifaDia(4000)
				.build();
		ServicioParqueoEntity servicioParqueoEntityMoto = new ServicioParqueoEntity(servicioParqueoMoto);		
	}
	
	@Test
	public void comprobarSiRegistraFacturaEntradaCarro() throws EstacionamientoException{
		
		//Arrange
		
		FacturaParqueoEntity facturaParqueoCreada = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo();
		peticionServicioParqueo.setPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA);
		peticionServicioParqueo.setTipoVehiculo(1);
		
		//Action
		
		facturaParqueoCreada = estacionamientoController.registrarEntradaEstacionamiento(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(facturaParqueoCreada);
	}
}
