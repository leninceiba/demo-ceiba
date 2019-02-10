package com.estacionamiento.model;

import com.estacionamiento.entity.ServicioParqueoEntity;

public class ServicioParqueo {
	
	private long id;
	private String descripcion;
	private long tarifaHora;
	private long tarifaDia;
	private int cupoMaximo;
	private int cupoDisponible;
	private String error;		
	private PeticionServicioParqueo peticionServicioParqueo;
	
	public ServicioParqueo(){
		
	}
	
	public ServicioParqueo(String descripcion, long tarifaHora, long tarifaDia, int cupoMaximo, int cupoDisponible, String error, PeticionServicioParqueo peticionServicioParqueo){
		super();
		this.descripcion = descripcion;
		this.tarifaHora = tarifaHora;
		this.tarifaDia = tarifaDia;
		this.cupoMaximo = cupoMaximo;
		this.cupoDisponible = cupoDisponible;
		this.error = error;
		this.peticionServicioParqueo = peticionServicioParqueo;
	}
	
	public ServicioParqueo(ServicioParqueoEntity servicioParqueoEntity){
		super();
		this.id = servicioParqueoEntity.getId();
		this.descripcion = servicioParqueoEntity.getDescripcion();
		this.tarifaHora = servicioParqueoEntity.getTarifaHora();
		this.tarifaDia = servicioParqueoEntity.getTarifaDia();
		this.cupoMaximo = servicioParqueoEntity.getCupoMaximo();
		this.cupoDisponible = servicioParqueoEntity.getCupoDisponible();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getTarifaHora() {
		return tarifaHora;
	}

	public void setTarifaHora(long tarifaHora) {
		this.tarifaHora = tarifaHora;
	}

	public long getTarifaDia() {
		return tarifaDia;
	}

	public void setTarifaDia(long tarifaDia) {
		this.tarifaDia = tarifaDia;
	}

	public int getCupoDisponible() {
		return cupoDisponible;
	}

	public void setCupoDisponible(int cupoDisponible) {
		this.cupoDisponible = cupoDisponible;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public PeticionServicioParqueo getPeticionServicioParqueo() {
		return peticionServicioParqueo;
	}

	public void setPeticionServicioParqueo(PeticionServicioParqueo peticionServicioParqueo) {
		this.peticionServicioParqueo = peticionServicioParqueo;
	}

	public int getCupoMaximo() {
		return cupoMaximo;
	}

	public void setCupoMaximo(int cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
	}

}
