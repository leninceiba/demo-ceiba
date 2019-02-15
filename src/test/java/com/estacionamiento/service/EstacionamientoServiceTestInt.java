package com.estacionamiento.service;

import static org.mockito.Mockito.when;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.estacionamiento.builders.FacturaParqueoBuild;
import com.estacionamiento.builders.ServicioParqueoBuild;
import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.controller.EstacionamientoController;
import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.FacturaParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.repository.FacturaParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;
import com.estacionamiento.service.impl.EstacionamientoServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EstacionamientoServiceTestInt {
	
	@Mock
	ServicioParqueoRepository servicioParqueoRepository;
	
	@Mock
	FacturaParqueoRepository facturaParqueoRepository;
	
	@InjectMocks
	EstacionamientoServiceImpl estacionamientoServiceImpl;
	
	@InjectMocks
	EstacionamientoController estacionamientoController;
	
	@InjectMocks
	EstacionamientoUtil estacionamientoUtil;
	
	private FacturaParqueoEntity facturaParqueoEntityCarro;
	private ServicioParqueoEntity servicioParqueoEntityCarro;
	private ServicioParqueo servicioParqueoCarro;
	private ServicioParqueoEntity servicioParqueoEntityMoto;
	private ServicioParqueo servicioParqueoMoto;
	
	@Before
	public void setupEntity(){

		MockitoAnnotations.initMocks(this);
		servicioParqueoCarro = new ServicioParqueoBuild()
				.withCodigo(1)
				.withCupoMaximo(20)
				.withCupoDisponible(20)
				.withDescripcion("carro")
				.withTarifaHora(1000)
				.withTarifaDia(8000)
				.build();
		servicioParqueoEntityCarro = new ServicioParqueoEntity(servicioParqueoCarro);	
		
		servicioParqueoMoto = new ServicioParqueoBuild()
				.withCodigo(2)
				.withCupoMaximo(10)
				.withCupoDisponible(10)
				.withDescripcion("moto")
				.withTarifaHora(500)
				.withTarifaDia(4000)
				.build();
		servicioParqueoEntityMoto = new ServicioParqueoEntity(servicioParqueoMoto);	
		
		FacturaParqueo facturaParqueoCreadaCarro = new FacturaParqueoBuild()
				.withEstado(EstacionamientoUtil.ESTADO_PENDIENTE)
				.withFechaEntrada(EstacionamientoUtil.FECHA_ENTRADA)
				.withPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA)
				.withServicioParqueo(servicioParqueoCarro).buildFacturaParqueoCarro();
		
		facturaParqueoEntityCarro = new FacturaParqueoEntity(facturaParqueoCreadaCarro);
	}
	
	@Test
	public void comprobarSiRegistraFacturaEntradaCarro() throws EstacionamientoException{
		
		//Arrange
		
		FacturaParqueoEntity facturaParqueoEntityRespuesta = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo();
		peticionServicioParqueo.setPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA);
		peticionServicioParqueo.setTipoVehiculo(1);
		
		//Action
		
		when(facturaParqueoRepository.findByPlacaVehiculoByEstado(Mockito.anyString(), Mockito.anyString())).thenReturn(facturaParqueoEntityCarro);
		when(servicioParqueoRepository.findByCodigo(Mockito.anyInt())).thenReturn(servicioParqueoEntityCarro);
		facturaParqueoEntityRespuesta = estacionamientoServiceImpl.registrarEntradaEstacionamiento(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertTrue(Objects.nonNull(facturaParqueoEntityRespuesta));
	}
}
