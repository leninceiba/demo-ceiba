package com.estacionamiento.builders;

import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;

public class ServicioParqueoBuild {

	private int codigo;
	private String descripcion;
	private long tarifaHora;
	private long tarifaDia;
	private int cupoMaximo;
	private int cupoDisponible;
	private PeticionServicioParqueo peticionServicioParqueo;

	

	public ServicioParqueoBuild() {
		super();
		this.codigo = 1;
		this.descripcion = "carro";
		this.tarifaHora = 1000;
		this.tarifaDia = 8000;
		this.cupoMaximo = 20;
		this.cupoDisponible = 20;
	}

	public ServicioParqueoBuild withCodigo(int codigo) {
		this.codigo = codigo;
		return this;
	}

	public ServicioParqueoBuild withDescripcion(String descripcion) {
		this.descripcion = descripcion;
		return this;
	}
	
	public ServicioParqueoBuild withTarifaHora(long tarifaHora) {
		this.tarifaHora = tarifaHora;
		return this;
	}
	
	public ServicioParqueoBuild withTarifaDia(long tarifaDia) {
		this.tarifaDia = tarifaDia;
		return this;
	}
	
	public ServicioParqueoBuild withCupoMaximo(int cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
		return this;
	}
	
	public ServicioParqueoBuild withCupoDisponible(int cupoDisponible) {
		this.cupoDisponible = cupoDisponible;
		return this;
	}
	
	public ServicioParqueoBuild withPeticionServicioParqueo(PeticionServicioParqueo peticionServicioParqueo) {
		this.peticionServicioParqueo = peticionServicioParqueo;
		return this;
	}	
	
	public ServicioParqueo build(){
		return new ServicioParqueo(codigo, descripcion, tarifaHora, tarifaDia, cupoMaximo, cupoDisponible, peticionServicioParqueo);
	}
	
	public static ServicioParqueoBuild aServicioParqueoBuild(){
		return new ServicioParqueoBuild();		
	}

}
