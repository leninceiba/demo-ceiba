package com.estacionamiento.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;
import org.springframework.test.context.junit4.SpringRunner;

import com.estacionamiento.builders.VehiculoParqueoBuild;
import com.estacionamiento.builders.ServicioParqueoBuild;
import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.controller.EstacionamientoController;
import com.estacionamiento.entity.VehiculoParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.model.TiempoServicio;
import com.estacionamiento.repository.VehiculoParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;
import com.estacionamiento.service.impl.EstacionamientoServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EstacionamientoServiceTest {
	
	@MockBean
	ServicioParqueoRepository servicioParqueoRepository;
	
	@MockBean
	VehiculoParqueoRepository vehiculoParqueoRepository;
	
	@Autowired
	EstacionamientoServiceImpl estacionamientoServiceImpl;
	
	@Autowired
	EstacionamientoController estacionamientoController;	
	
	private VehiculoParqueoEntity vehiculoParqueoEntityCarro;
	private ServicioParqueoEntity servicioParqueoEntityCarro;
	private ServicioParqueo servicioParqueoCarro;
	private VehiculoParqueoEntity vehiculoParqueoEntityMoto;
	private ServicioParqueoEntity servicioParqueoEntityMoto;
	private ServicioParqueo servicioParqueoMoto;
	private List<VehiculoParqueoEntity> listaVehiculoParqueoEntity;	
	
	@Before
	public void setupEntity(){

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
	public void comprobarSiExisteVehiculoParqueado(){
		
		//Arrange
		
		VehiculoParqueoEntity facturaParqueoExiste = null;
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).build();
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		VehiculoParqueo vehiculoParqueo = new VehiculoParqueoBuild()
				.withEstado(EstacionamientoUtil.ESTADO_PENDIENTE)
				.withPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA)
				.withFechaEntrada(peticionServicioParqueo.getFecha())
				.withServicioParqueo(servicioParqueo)
				.withFechaSalida(null).buildVehiculoParqueoCarro();
		
		//Action
		
		when(vehiculoParqueoRepository.findByPlacaVehiculoByEstado(peticionServicioParqueo.getPlacaVehiculo(),EstacionamientoUtil.ESTADO_PENDIENTE)).thenReturn(new VehiculoParqueoEntity(vehiculoParqueo));
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
	
	@Test
	public void comprobarFormatoFechaCalendar(){
		
		//Arrange
		
		String formato = "dd-M-yyyy HH:mm:ss";
		String fecha = "12-03-2019 12:00:00";
		Calendar fechaFormateada;
		
		//Action
		
		fechaFormateada = EstacionamientoUtil.getFechaCalendar(formato, fecha);
		
		//Assert
		
		Assert.assertNotNull(fechaFormateada);
	}
	
	@Test
	public void comprobarCalculoTiempoServicio(){
		
		//Arrange
		
		Calendar fechaEntrada = EstacionamientoUtil.FECHA_ENTRADA;
		Calendar fechaSalida = EstacionamientoUtil.FECHA_SALIDA;
		TiempoServicio tiempoServicioCalculado = null;
		
		//Action
		
		tiempoServicioCalculado = EstacionamientoUtil.calcularTiempoServicio(fechaEntrada, fechaSalida);
		
		//Assert
		
		Assert.assertEquals(1,tiempoServicioCalculado.getDias());
		Assert.assertEquals(3,tiempoServicioCalculado.getHoras());
	}
	
	@Test
	public void comprobarSiFechaEsDomingoOLunes(){
		
		//Arrange
		
		String formato = "dd-M-yyyy HH:mm:ss";
		String fechaX = "10-03-2019 12:00:00";
		String fechaY = "12-03-2019 12:00:00";
		Calendar fechaXFormateada;
		Calendar fechaYFormateada;
		boolean fechaXEsDomingoOLunes;
		boolean fechaYEsDomingoOLunes;
		
		//Action
		fechaXFormateada = EstacionamientoUtil.getFechaCalendar(formato, fechaX);
		fechaXEsDomingoOLunes = EstacionamientoUtil.esDomingoOLunes(fechaXFormateada);
		fechaYFormateada = EstacionamientoUtil.getFechaCalendar(formato, fechaY);
		fechaYEsDomingoOLunes = EstacionamientoUtil.esDomingoOLunes(fechaYFormateada);
		
		//Assert
		
		Assert.assertEquals(true, fechaXEsDomingoOLunes);
		Assert.assertEquals(false, fechaYEsDomingoOLunes);
	}
	
	@Test
	public void comprobarRegistroEntradaEstacionamiento() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueoEntity vehiculoParqueoEntityRespuesta = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo();
		peticionServicioParqueo.setPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA);
		peticionServicioParqueo.setTipoVehiculo(2);
		
		//Action
		
		vehiculoParqueoEntityRespuesta = estacionamientoController.registrarEntradaEstacionamiento(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(vehiculoParqueoEntityRespuesta);
	}
	
	@Test
	public void comprobarConsultarVehiculos() throws EstacionamientoException{
		
		//Arrange
		
		List<VehiculoParqueoEntity> listaVehiculoParqueo = null;
		
		//Action
		
		listaVehiculoParqueo = estacionamientoController.consultarVehiculos();
		
		//Assert
		
		Assert.assertNotNull(listaVehiculoParqueo);
	}

}
