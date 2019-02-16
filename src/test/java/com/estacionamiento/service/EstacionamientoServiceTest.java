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
import com.estacionamiento.model.VehiculoParqueoCarro;
import com.estacionamiento.model.VehiculoParqueoMoto;
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
	
	public Calendar FECHA_ENTRADA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","12-03-2019 12:00:00");
	public Calendar FECHA_SALIDA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","13-03-2019 15:00:00");	
	
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
		
		Calendar fechaEntrada = FECHA_ENTRADA;
		Calendar fechaSalida = FECHA_SALIDA;
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
		peticionServicioParqueo.setTipoVehiculo(1);
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).withCupoDisponible(20).build();
		
		//Action
		
		when(servicioParqueoRepository.findByCodigo(servicioParqueo.getCodigo())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
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
		vehiculoParqueo.setFechaEntrada(FECHA_ENTRADA);
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
	
	@Test
	public void comprobarSeteosVehiculoParqueoCarro(){
		
		//Arrange
		
		VehiculoParqueoCarro vehiculoParqueoCarro = null;
		
		//Action
		
		vehiculoParqueoCarro = new VehiculoParqueoCarro();
		vehiculoParqueoCarro.setPlaca(EstacionamientoUtil.PLACA_PRUEBA);
		
		//Assert
		
		Assert.assertNotNull(vehiculoParqueoCarro);
	}
	
	@Test
	public void comprobarSeteosVehiculoParqueoMoto(){
		
		//Arrange
		
		VehiculoParqueoMoto vehiculoParqueoMoto = null;
		
		//Action
		
		vehiculoParqueoMoto = new VehiculoParqueoMoto();
		vehiculoParqueoMoto.setPlaca(EstacionamientoUtil.PLACA_PRUEBA);
		vehiculoParqueoMoto.setCilindraje(EstacionamientoUtil.RANGO_CILINDRAJE_APLICA_RECARGO);
		
		//Assert
		
		Assert.assertNotNull(vehiculoParqueoMoto);
	}
	
	@Test
	public void comprobarSeteosVehiculoParqueoEntity(){
		
		//Arrange
		
		VehiculoParqueoEntity vehiculoParqueoEntity = null;
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().build();
		ServicioParqueoEntity servicioParqueoEntity = new ServicioParqueoEntity(servicioParqueo);
		
		//Action
		
		vehiculoParqueoEntity = new VehiculoParqueoEntity();
		vehiculoParqueoEntity.setId(18);
		vehiculoParqueoEntity.setFechaEntrada(FECHA_ENTRADA);
		vehiculoParqueoEntity.setEstado(EstacionamientoUtil.ESTADO_PENDIENTE);
		vehiculoParqueoEntity.setValorServicio(18000);
		vehiculoParqueoEntity.setTiempoServicio(18);
		vehiculoParqueoEntity.setPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA);
		vehiculoParqueoEntity.setCilindrajeMoto(EstacionamientoUtil.RANGO_CILINDRAJE_APLICA_RECARGO);
		vehiculoParqueoEntity.setError("comprobacion en test");
		vehiculoParqueoEntity.setServicioParqueoEntity(servicioParqueoEntity);
		
		//Assert
		
		Assert.assertNotNull(vehiculoParqueoEntity);
		Assert.assertEquals("comprobacion en test",vehiculoParqueoEntity.getError());
		Assert.assertEquals(18,vehiculoParqueoEntity.getTiempoServicio());
	}
	
	@Test
	public void comprobarCalculoValorTotalAPagar(){
		
		//Arrange
		
		long valorTotalAPagar;
		TiempoServicio tiempoServicio = new TiempoServicio(23,0);
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().build();	
		VehiculoParqueo vehiculoParqueo = new VehiculoParqueo();
		
		//Action

		vehiculoParqueo.setServicioParqueo(servicioParqueo);
		valorTotalAPagar = vehiculoParqueo.calcularValorTotalAPagar(tiempoServicio);
		
		//Assert
		
		Assert.assertNotNull(valorTotalAPagar);
	}
	
	@Test
	public void comprobarSeteosServicioParqueo(){
		
		//Arrange
		
		ServicioParqueo servicioParqueo = null;
		
		//Action
		
		servicioParqueo = new ServicioParqueo();
		servicioParqueo.setId(18);
		servicioParqueo.setCodigo(1);
		servicioParqueo.setDescripcion("carro");
		servicioParqueo.setCupoMaximo(20);
		servicioParqueo.setCupoDisponible(20);
		servicioParqueo.setTarifaDia(8000);
		servicioParqueo.setTarifaHora(1000);
		
		//Assert
		
		Assert.assertNotNull(servicioParqueo);
	}
	
	@Test
	public void comprobarSeteosPeticionServicioParqueo(){
		
		//Arrange
		
		PeticionServicioParqueo peticionServicioParqueo = null;
		
		//Action
		
		peticionServicioParqueo = new PeticionServicioParqueo();
		peticionServicioParqueo.setCilindrajeMoto(String.valueOf(EstacionamientoUtil.RANGO_CILINDRAJE_APLICA_RECARGO));
		peticionServicioParqueo.setError("comprobacion en test");
		peticionServicioParqueo.setFecha(FECHA_ENTRADA);		
		
		//Assert
		
		Assert.assertNotNull(peticionServicioParqueo);
		Assert.assertEquals("comprobacion en test",peticionServicioParqueo.getError());
	}
	
	@Test
	public void comprobarSeteosServicioParqueoEntity(){
		
		//Arrange
		
		ServicioParqueoEntity servicioParqueoEntity = null;
		
		//Action
		
		servicioParqueoEntity = new ServicioParqueoEntity();
		servicioParqueoEntity.setId(18);
		servicioParqueoEntity.setCodigo(1);
		servicioParqueoEntity.setDescripcion("carro");
		servicioParqueoEntity.setCupoMaximo(20);
		servicioParqueoEntity.setCupoDisponible(20);
		servicioParqueoEntity.setTarifaDia(8000);
		servicioParqueoEntity.setTarifaHora(1000);
		
		//Assert
		
		Assert.assertNotNull(servicioParqueoEntity);
	}
	
	@Test
	public void comprobarRecargoCilindraje(){
		
		//Arrange
		
		VehiculoParqueoMoto vehiculoParqueoMoto = new VehiculoParqueoMoto();
		
		//Action

		vehiculoParqueoMoto.setCilindraje(EstacionamientoUtil.RANGO_CILINDRAJE_APLICA_RECARGO);
		vehiculoParqueoMoto.setValorServicio(18000);
		vehiculoParqueoMoto.aplicarRecargoPorCilindraje();
		
		//Assert	
		
		Assert.assertEquals(18000,vehiculoParqueoMoto.getValorServicio());
	}

}
