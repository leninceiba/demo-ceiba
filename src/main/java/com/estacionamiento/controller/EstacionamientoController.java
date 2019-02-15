package com.estacionamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento.entity.VehiculoParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.service.IEstacionamientoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class EstacionamientoController {	
	
	@Autowired
	IEstacionamientoService estacionamientoService;	
	
	@PostMapping("/registroEntrada")	
	public VehiculoParqueoEntity registrarEntradaEstacionamiento(@RequestBody PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException{
		
		return this.estacionamientoService.registrarEntradaEstacionamiento(peticionServicioParqueo);
	}
	
	@GetMapping("/registroSalida/{idFactura}")	
	public VehiculoParqueo registrarSalidaEstacionamiento(@PathVariable long idFactura) throws EstacionamientoException{
		
		return this.estacionamientoService.registrarSalidaEstacionamiento(idFactura);		
	}
	
	@GetMapping("/consultaVehiculos")
	public List<VehiculoParqueoEntity> consultarVehiculos() throws EstacionamientoException{
		
		return this.estacionamientoService.consultarVehiculos();
	}
	
}
