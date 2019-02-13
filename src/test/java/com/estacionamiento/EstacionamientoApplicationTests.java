package com.estacionamiento;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import com.estacionamiento.controller.EstacionamientoController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EstacionamientoApplicationTests {
	
	@Autowired
	private EstacionamientoController estacionamientoController;

	@Test
	public void contextLoads() {
		
		assertThat(estacionamientoController).isNotNull();
	}

}

