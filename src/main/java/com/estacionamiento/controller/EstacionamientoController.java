package com.estacionamiento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.FacturaParqueo;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.service.IEstacionamientoService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class EstacionamientoController {	
	
	@Autowired
	IEstacionamientoService estacionamientoService;	
	
	@GetMapping("/")
	public String bienvenida(){
		
		return ("<div style=\"text-align : center\">::::+> Bienvenido a tu Estacionamiento Preferido <+::::</div>");
	}
	
	@PostMapping("/registroEntrada")	
	public FacturaParqueoEntity registrarEntradaEstacionamiento(@RequestBody PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException{
		
		return this.estacionamientoService.registrarEntradaEstacionamiento(peticionServicioParqueo);
	}
	
	@GetMapping("/registroSalida")	
	public FacturaParqueo registrarSalidaEstacionamiento(@RequestParam ("id") Long idFactura) throws EstacionamientoException{
		
		return this.estacionamientoService.registrarSalidaEstacionamiento(idFactura);		
	}
	
	@GetMapping("/consultaFacturas")
	public List<FacturaParqueoEntity> consultarFacturas() throws EstacionamientoException{
		
		return this.estacionamientoService.consultarFacturas();
	}
	
}
