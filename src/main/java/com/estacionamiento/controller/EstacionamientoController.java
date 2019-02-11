package com.estacionamiento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.model.ServicioParqueo;
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
	
	@PostMapping("/registro")	
	public FacturaParqueoEntity registrarEntradaEstacionamiento(@RequestBody ServicioParqueo servicioParqueo) throws Exception{
		return this.estacionamientoService.registrarEntradaEstacionamiento(servicioParqueo);		
	}
}