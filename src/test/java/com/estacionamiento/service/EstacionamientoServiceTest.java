package com.estacionamiento.service;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamiento.builders.FacturaParqueoBuild;
import com.estacionamiento.builders.ServicioParqueoBuild;
import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.controller.EstacionamientoController;
import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.model.FacturaParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.repository.FacturaParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;
import com.estacionamiento.service.impl.EstacionamientoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EstacionamientoServiceTest {
	
	@MockBean
	ServicioParqueoRepository servicioParqueoRepository;
	
	@MockBean
	FacturaParqueoRepository facturaParqueoRepository;
	
	@Autowired
	EstacionamientoServiceImpl estacionamientoServiceImpl;
	
	@Autowired
	EstacionamientoController estacionamientoController;	
	
	@Test
	public void comprobarSiExisteVehiculoParqueado(){
		
		//Arrange
		
		FacturaParqueoEntity facturaParqueoExiste = null;
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).build();
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		FacturaParqueo facturaParqueo = new FacturaParqueoBuild()
				.withEstado(EstacionamientoUtil.ESTADO_PENDIENTE)
				.withPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA)
				.withFechaEntrada(peticionServicioParqueo.getFecha())
				.withServicioParqueo(servicioParqueo)
				.withFechaSalida(null).buildFacturaParqueoCarro();
		
		//Action
		
		when(facturaParqueoRepository.findByPlacaVehiculoByEstado(peticionServicioParqueo.getPlacaVehiculo(),EstacionamientoUtil.ESTADO_PENDIENTE)).thenReturn(new FacturaParqueoEntity(facturaParqueo));
		facturaParqueoExiste = estacionamientoServiceImpl.existeVehiculoParqueado(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(facturaParqueoExiste);		
	}
	
	@Test
	public void comprobarDisponibilidadServicioTipoCarro(){
		
		//Arrange
		
		ServicioParqueo servicioParqueoEncontrado = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).build();
		
		//Action
		
		when(servicioParqueoRepository.findByCodigo(servicioParqueo.getCodigo())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
		servicioParqueoEncontrado = estacionamientoServiceImpl.comprobarDisponibilidadParqueo(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(servicioParqueoEncontrado);
	}
	
	@Test
	public void comprobarSinCupoServicioTipoCarro(){
		
		//Arrange
		
		ServicioParqueo servicioParqueoEncontrado = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).withCupoDisponible(0).build();
		
		//Action
		
		when(servicioParqueoRepository.findByCodigo(servicioParqueo.getCodigo())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
		servicioParqueoEncontrado = estacionamientoServiceImpl.comprobarDisponibilidadParqueo(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertEquals(EstacionamientoUtil.SIN_CUPO, servicioParqueoEncontrado.getError());
	}
	
	@Test
	public void comprobarRestriccionPlacaVehiculo(){
		
		//Arrange
		
		ServicioParqueo servicioParqueoEncontrado = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_EMPIEZA_CON_A, null, 1, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).build();
		
		//Action
		
		when(servicioParqueoRepository.findByCodigo(servicioParqueo.getCodigo())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
		servicioParqueoEncontrado = estacionamientoServiceImpl.comprobarDisponibilidadParqueo(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertEquals(EstacionamientoUtil.PLACA_NO_AUTORIZADA, servicioParqueoEncontrado.getError());
	}

}
