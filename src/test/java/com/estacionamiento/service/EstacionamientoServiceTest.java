package com.estacionamiento.service;

import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.estacionamiento.builders.ServicioParqueoBuild;
import com.estacionamiento.builders.VehiculoParqueoBuild;
import com.estacionamiento.bussines.EstacionamientoBussines;
import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.entity.VehiculoParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.model.TiempoServicio;
import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.model.VehiculoParqueoCarro;
import com.estacionamiento.model.VehiculoParqueoMoto;


@RunWith(MockitoJUnitRunner.Silent.class)
public class EstacionamientoServiceTest {
	
	@Mock
	EstacionamientoService estacionamientoService;	
	
	@InjectMocks
	EstacionamientoBussines estacionamientoBussines;
	
	public Calendar FECHA_ENTRADA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","12-03-2019 12:00:00");
	public Calendar FECHA_SALIDA = EstacionamientoUtil.getFechaCalendar("dd-M-yyyy HH:mm:ss","13-03-2019 15:00:00");	
	
	@Before
	public void setupEntity() {

		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void comprobarSiExisteVehiculoParqueado() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueo vehiculoParqueoExiste = null;
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).build();
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		VehiculoParqueo vehiculoParqueo = new VehiculoParqueoBuild()
				.withEstado(EstacionamientoUtil.ESTADO_PENDIENTE)
				.withPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA)
				.withFechaEntrada(peticionServicioParqueo.getFecha())
				.withServicioParqueo(servicioParqueo)
				.withFechaSalida(null).buildVehiculoParqueoCarro();
		
		//Action
		
		when(estacionamientoService.findVehiculoParqueoByPlacaByEstado(Mockito.anyString(),Mockito.anyString())).thenReturn(new VehiculoParqueoEntity(vehiculoParqueo));
		vehiculoParqueoExiste = estacionamientoBussines.registrarEntradaEstacionamiento(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertEquals(EstacionamientoUtil.EXISTE_VEHICULO,vehiculoParqueoExiste.getError());		
	}
	
	@Test
	public void comprobarDisponibilidadServicioTipoCarro() throws EstacionamientoException{
		
		//Arrange
		
		ServicioParqueo servicioParqueoEncontrado = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).build();
		
		//Action
		
		when(estacionamientoService.findServicioParqueoByCodigo(Mockito.anyInt())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
		servicioParqueoEncontrado = estacionamientoBussines.comprobarCupoDisponible(peticionServicioParqueo.getTipoVehiculo());
		
		//Assert
		
		Assert.assertEquals(servicioParqueo.getCodigo(), servicioParqueoEncontrado.getCodigo());
	}
	
	@Test
	public void comprobarSinCupoServicioTipoCarro() throws EstacionamientoException{
		
		//Arrange
		
		ServicioParqueo servicioParqueoEncontrado = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).withCupoDisponible(0).build();
		//Action
		
		when(estacionamientoService.findServicioParqueoByCodigo(Mockito.anyInt())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
		servicioParqueoEncontrado = estacionamientoBussines.comprobarCupoDisponible(peticionServicioParqueo.getTipoVehiculo());
		
		//Assert
		
		System.out.println("servicioParqueoEncontrado.getError() ::::+> "+servicioParqueoEncontrado.getError());
		Assert.assertEquals(EstacionamientoUtil.SIN_CUPO, servicioParqueoEncontrado.getError());
	}
	
//	@Test
//	public void comprobarRestriccionPlacaVehiculo(){
//		
//		//Arrange
//		
//		ServicioParqueo servicioParqueoEncontrado = null;
//		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_EMPIEZA_CON_A, null, 1, Calendar.getInstance());
//		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).build();
//		
//		//Action
//		
//		when(estacionamientoService.findServicioParqueoByCodigo(Mockito.anyInt())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
//		servicioParqueoEncontrado = estacionamientoBussines.comprobarDisponibilidadParqueo(peticionServicioParqueo);
//		
//		//Assert
//		
//		Assert.assertEquals(EstacionamientoUtil.PLACA_NO_AUTORIZADA, servicioParqueoEncontrado.getError());
//	}
	
	@Test
	public void comprobarFormatoFechaCalendar(){
		
		//Arrange
		
		int laFechaEsSuperiorALaFormateada = 1;
		String formato = "dd-M-yyyy HH:mm:ss";
		String fecha = "12-03-2019 12:00:00";
		Calendar fechaFormateada;
		
		//Action
		
		fechaFormateada = EstacionamientoUtil.getFechaCalendar(formato, fecha);
		
		//Assert
		System.out.println("fechaFormateada.getTime() ::::+> "+fechaFormateada.getTime().compareTo(Calendar.getInstance().getTime()));
		Assert.assertEquals(laFechaEsSuperiorALaFormateada, fechaFormateada.getTime().compareTo(Calendar.getInstance().getTime()));
	}
	
	@Test
	public void comprobarCalculoTiempoServicio(){
		
		//Arrange
		
		int unDia = 1;
		int tresHoras = 3;
		Calendar fechaEntrada = FECHA_ENTRADA;
		Calendar fechaSalida = FECHA_SALIDA;
		TiempoServicio tiempoServicioCalculado = null;
		
		//Action
		
		tiempoServicioCalculado = EstacionamientoUtil.calcularTiempoServicio(fechaEntrada, fechaSalida);
		
		//Assert
		
		Assert.assertEquals(unDia,tiempoServicioCalculado.getDias());
		Assert.assertEquals(tresHoras,tiempoServicioCalculado.getHoras());
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
		
		VehiculoParqueo vehiculoParqueoRespuesta = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo();
		peticionServicioParqueo.setPlacaVehiculo(EstacionamientoUtil.PLACA_PRUEBA);
		peticionServicioParqueo.setTipoVehiculo(1);
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).withCupoDisponible(20).build();
		
		//Action
		
		when(estacionamientoService.findServicioParqueoByCodigo(Mockito.anyInt())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
		vehiculoParqueoRespuesta = estacionamientoBussines.registrarEntradaEstacionamiento(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertNull(vehiculoParqueoRespuesta.getError());
	}
	
	@Test
	public void comprobarConsultarVehiculos(){
		
		//Arrange
		
		List<VehiculoParqueoEntity> listaVehiculoParqueo = null;
		
		//Action
		
		listaVehiculoParqueo = estacionamientoBussines.consultarVehiculos();
		
		//Assert
		
		Assert.assertNotNull(listaVehiculoParqueo);
	}
	
	@Test
	public void comprobarCreacionVehiculoParqueoCarro() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueoEntity vehiculoParqueo = null;
		PeticionServicioParqueo peticionServicioParqueoCarro = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, null, 1, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild()
				.withCodigo(1)
				.withPeticionServicioParqueo(peticionServicioParqueoCarro).build();
		
		//Action
		
		vehiculoParqueo = estacionamientoBussines.inicializarVehiculoParqueo(servicioParqueo);
		
		//Assert
		
		Assert.assertEquals(vehiculoParqueo.getClass().getSimpleName(),"VehiculoParqueoCarro");
	}
	
	@Test
	public void comprobarCreacionVehiculoParqueoMoto() throws EstacionamientoException{
		
		//Arrange
		
		VehiculoParqueo vehiculoParqueo = null;
		PeticionServicioParqueo peticionServicioParqueoMoto = new PeticionServicioParqueo(EstacionamientoUtil.PLACA_PRUEBA, String.valueOf(EstacionamientoUtil.RANGO_CILINDRAJE_APLICA_RECARGO), 2, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild()
				.withCodigo(2)
				.withPeticionServicioParqueo(peticionServicioParqueoMoto).build();
		
		//Action
		
		vehiculoParqueo = estacionamientoBussines.inicializarVehiculoParqueo(servicioParqueo);
		
		//Assert

		Assert.assertEquals(vehiculoParqueo.getClass().getSimpleName(),"VehiculoParqueoMoto");
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
