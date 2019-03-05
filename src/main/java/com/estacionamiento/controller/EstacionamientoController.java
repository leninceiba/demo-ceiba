package com.estacionamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estacionamiento.bussines.EstacionamientoBussines;
import com.estacionamiento.entity.VehiculoParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
public class EstacionamientoController {

	@Value("{Bienvenido a tu estacionamiento favorito, perro!!}")
	String valueExample = null;

	@Autowired
	EstacionamientoBussines estacionamientoBussines;

	@RequestMapping("/")
	public String bienvenida() {
		return valueExample;
	}

	@PostMapping("/registroentrada")
	public VehiculoParqueo registrarEntradaEstacionamiento(@RequestBody PeticionServicioParqueo peticionServicioParqueo)
			throws EstacionamientoException {

		return this.estacionamientoBussines.registrarEntradaEstacionamiento(peticionServicioParqueo);
	}

	@GetMapping("/registrosalida/{idFactura}")
	public VehiculoParqueo registrarSalidaEstacionamiento(@PathVariable long idFactura)
			throws EstacionamientoException {

		return this.estacionamientoBussines.registrarSalidaEstacionamiento(idFactura);
	}

	@GetMapping("/consultavehiculos")
	public List<VehiculoParqueoEntity> consultarVehiculos() {

		return this.estacionamientoBussines.consultarVehiculos();
	}

}
