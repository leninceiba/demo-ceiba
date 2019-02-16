package com.estacionamiento.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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

import com.estacionamiento.builders.VehiculoParqueoBuild;
import com.estacionamiento.builders.ServicioParqueoBuild;
import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.controller.EstacionamientoController;
import com.estacionamiento.entity.VehiculoParqueoEntity;
import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.repository.VehiculoParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;
import com.estacionamiento.service.impl.EstacionamientoServiceImpl;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EstacionamientoServiceTestInt {
	
	@Mock
	ServicioParqueoRepository servicioParqueoRepository;
	
	@Mock
	VehiculoParqueoRepository vehiculoParqueoRepository;
	
	@InjectMocks
	EstacionamientoServiceImpl estacionamientoServiceImpl;
	
	@InjectMocks
	EstacionamientoController estacionamientoController;
	
	@InjectMocks
	EstacionamientoUtil estacionamientoUtil;
	
	private VehiculoParqueoEntity vehiculoParqueoEntityCarro;
	private ServicioParqueoEntity servicioParqueoEntityCarro;
	private ServicioParqueo servicioParqueoCarro;
	private VehiculoParqueoEntity vehiculoParqueoEntityMoto;
	private ServicioParqueoEntity servicioParqueoEntityMoto;
	private ServicioParqueo servicioParqueoMoto;
	private List<VehiculoParqueoEntity> listaVehiculoParqueoEntity;
	
	@Before
	public void setupEntity(){

		MockitoAnnotations.initMocks(this);
		listaVehiculoParqueoEntity = new ArrayList<VehiculoParqueoEntity>();
		servicioParqueoCarro = new ServicioParqueoBuild()
				.withCodigo(1)
				.withCupoMaximo(20)
				.withCupoDisponible(20)
				.withDescripcion("carro")
				.withTarifaHora(1000)
				.withTarifaDia(8000)
				.build();
		servicioParqueoEntityCarro = new ServicioParqueoEntity(servicioParqueoCarro);	
		
		VehiculoParqueo vehiculoParqueoCreadaCarro = new VehiculoParqueoBuild()
				.withEstado(EstacionamientoUtil.ESTADO_PENDIENTE)
				.withFechaEntrada(EstacionamientoUtil.FECHA_ENTRADA)
				.withPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA)
				.withServicioParqueo(servicioParqueoCarro).buildVehiculoParqueoCarro();
		
		vehiculoParqueoEntityCarro = new VehiculoParqueoEntity(vehiculoParqueoCreadaCarro);
		listaVehiculoParqueoEntity.add(vehiculoParqueoEntityCarro);
		
		servicioParqueoMoto = new ServicioParqueoBuild()
				.withCodigo(2)
				.withCupoMaximo(10)
				.withCupoDisponible(10)
				.withDescripcion("moto")
				.withTarifaHora(500)
				.withTarifaDia(4000)
				.build();
		servicioParqueoEntityMoto = new ServicioParqueoEntity(servicioParqueoMoto);	
		
		VehiculoParqueo vehiculoParqueoCreadaMoto = new VehiculoParqueoBuild()
				.withEstado(EstacionamientoUtil.ESTADO_PENDIENTE)
				.withFechaEntrada(EstacionamientoUtil.FECHA_ENTRADA)
				.withPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA)
				.withServicioParqueo(servicioParqueoMoto).buildVehiculoParqueoMoto();
		
		vehiculoParqueoEntityMoto = new VehiculoParqueoEntity(vehiculoParqueoCreadaMoto);		
		listaVehiculoParqueoEntity.add(vehiculoParqueoEntityMoto);
	}
	
	@Test
	public void comprobarSiRegistraVehiculoEntradaCarro() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueoEntity vehiculoParqueoEntityRespuesta = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo();
		peticionServicioParqueo.setPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA);
		peticionServicioParqueo.setTipoVehiculo(1);
		
		//Action
		
		when(vehiculoParqueoRepository.findByPlacaVehiculoByEstado(Mockito.anyString(), Mockito.anyString())).thenReturn(vehiculoParqueoEntityCarro);
		when(servicioParqueoRepository.findByCodigo(Mockito.anyInt())).thenReturn(servicioParqueoEntityCarro);
		vehiculoParqueoEntityRespuesta = estacionamientoServiceImpl.registrarEntradaEstacionamiento(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertTrue(Objects.nonNull(vehiculoParqueoEntityRespuesta));
	}
	
	@Test
	public void comprobarSiRegistraVehiculoEntradaMoto() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueoEntity vehiculoParqueoEntityRespuesta = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo();
		peticionServicioParqueo.setPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA);
		peticionServicioParqueo.setTipoVehiculo(2);
		
		//Action
		
		when(vehiculoParqueoRepository.findByPlacaVehiculoByEstado(Mockito.anyString(), Mockito.anyString())).thenReturn(vehiculoParqueoEntityMoto);
		when(servicioParqueoRepository.findByCodigo(Mockito.anyInt())).thenReturn(servicioParqueoEntityMoto);
		vehiculoParqueoEntityRespuesta = estacionamientoServiceImpl.registrarEntradaEstacionamiento(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertTrue(Objects.nonNull(vehiculoParqueoEntityRespuesta));
	}
	
	@Test
	public void comprobarConsultarVehiculos() throws EstacionamientoException{
		
		//Arrange
		
		List<VehiculoParqueoEntity> listaVehiculoParqueo = null;
		
		//Action
		
		when(vehiculoParqueoRepository.findAll()).thenReturn(listaVehiculoParqueoEntity);
		listaVehiculoParqueo = estacionamientoServiceImpl.consultarVehiculos();
		
		//Assert
		
		Assert.assertNotNull(listaVehiculoParqueo);
	}
	
	@Test
	public void comprobarSiRegistraVehiculoSalidaMoto() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueo vehiculoParqueoRespuesta = null;
		
		//Action
		
		when(vehiculoParqueoRepository.findById(Mockito.anyLong())).thenReturn(vehiculoParqueoEntityMoto);
		vehiculoParqueoRespuesta = estacionamientoServiceImpl.registrarSalidaEstacionamiento(vehiculoParqueoEntityMoto.getId());
		
		//Assert
		
		Assert.assertTrue(Objects.nonNull(vehiculoParqueoRespuesta));
	}
}
