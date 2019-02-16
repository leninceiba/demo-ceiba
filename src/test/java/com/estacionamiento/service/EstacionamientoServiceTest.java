package com.estacionamiento.service;

import java.util.Calendar;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
	
	@Test
	public void comprobarSiExisteVehiculoParqueado(){
		
		//Arrange
		
		VehiculoParqueoEntity vehiculoParqueoExiste = null;
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
		vehiculoParqueoExiste = estacionamientoServiceImpl.existeVehiculoParqueado(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(vehiculoParqueoExiste);		
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
	
	@Test
	public void comprobarCreacionVehiculoParqueoCarro() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueoEntity vehiculoParqueoEntity = null;
		PeticionServicioParqueo peticionServicioParqueoCarro = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild()
				.withCodigo(1)
				.withPeticionServicioParqueo(peticionServicioParqueoCarro).build();
		
		//Action
		
		vehiculoParqueoEntity = estacionamientoServiceImpl.crearVehiculoParqueo(servicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(vehiculoParqueoEntity);
	}
	
	@Test
	public void comprobarCreacionVehiculoParqueoMoto() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueoEntity vehiculoParqueoEntity = null;
		PeticionServicioParqueo peticionServicioParqueoMoto = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, String.valueOf(EstacionamientoUtil.RANGO_CILINDRAJE_APLICA_RECARGO), 2, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild()
				.withCodigo(2)
				.withPeticionServicioParqueo(peticionServicioParqueoMoto).build();
		
		//Action
		
		vehiculoParqueoEntity = estacionamientoServiceImpl.crearVehiculoParqueo(servicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(vehiculoParqueoEntity);
	}
	
	@Test
	public void comprobarSeteosTiempoServicio(){
		
		//Arrange
		
		TiempoServicio tiempoServicio = null;
		
		//Action
		
		tiempoServicio = new TiempoServicio();
		tiempoServicio.setDias(18);
		tiempoServicio.setHoras(18);
		
		//Assert
		
		Assert.assertNotNull(tiempoServicio);
	}
	
	@Test
	public void comprobarSeteosVehiculoParqueo(){
		
		//Arrange
		
		VehiculoParqueo vehiculoParqueo = null;
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().build();
		
		//Action
		
		vehiculoParqueo = new VehiculoParqueo();
		vehiculoParqueo.setId(18);
		vehiculoParqueo.setFechaEntrada(EstacionamientoUtil.FECHA_ENTRADA);
		vehiculoParqueo.setEstado(EstacionamientoUtil.ESTADO_PENDIENTE);
		vehiculoParqueo.setValorServicio(18000);
		vehiculoParqueo.setTiempoServicioHoras(18);
		vehiculoParqueo.setError("comprobacion en test");
		vehiculoParqueo.setServicioParqueo(servicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(vehiculoParqueo);
		Assert.assertEquals(18,vehiculoParqueo.getId());
		Assert.assertEquals("comprobacion en test",vehiculoParqueo.getError());
	}

}
