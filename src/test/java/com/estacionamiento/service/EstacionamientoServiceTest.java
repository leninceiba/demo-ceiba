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

import com.estacionamiento.builders.ServicioParqueoBuild;
import com.estacionamiento.entity.ServicioParqueoEntity;
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
	
	@Test
	public void comprobarDisponibilidadVehiculoTipoCarro(){
		
		//Arrange
		
		ServicioParqueo servicioParqueoEncontrado = null;
		PeticionServicioParqueo peticionServicioParqueo = new PeticionServicioParqueo("DNP142", null, 1, Calendar.getInstance());
		ServicioParqueo servicioParqueo = new ServicioParqueoBuild().withCodigo(1).build();
		
		//Action
		
		when(servicioParqueoRepository.findByCodigo(servicioParqueo.getCodigo())).thenReturn(new ServicioParqueoEntity(servicioParqueo));
		servicioParqueoEncontrado = estacionamientoServiceImpl.comprobarDisponibilidadParqueo(peticionServicioParqueo);
		
		//Assert
		
		Assert.assertNotNull(servicioParqueoEncontrado);
	}

}
