package com.estacionamiento.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.estacionamiento.model.ServicioParqueo;

@Entity
@Table(name = "servicio_parqueo")
public class ServicioParqueoEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private int codigo;
	private String descripcion;
	private int cupoMaximo;
	private int cupoDisponible;
	private long tarifaHora;
	private long tarifaDia;	

	public ServicioParqueoEntity() {

	}

	public ServicioParqueoEntity(int codigo, String descripcion, int cupoMaximo, int cupoDisponible, long tarifaHora, long tarifaDia) {
		super();
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.cupoMaximo = cupoMaximo;
		this.cupoDisponible = cupoDisponible;
		this.tarifaHora = tarifaHora;
		this.tarifaDia = tarifaDia;
	}

	public ServicioParqueoEntity(ServicioParqueo servicioParqueo) {
		super();		
		this.id = servicioParqueo.getId();
		this.codigo = servicioParqueo.getCodigo();
		this.descripcion = servicioParqueo.getDescripcion();
		this.cupoMaximo = servicioParqueo.getCupoMaximo();
		this.cupoDisponible = servicioParqueo.getCupoDisponible();
		this.tarifaHora = servicioParqueo.getTarifaHora();
		this.tarifaDia = servicioParqueo.getTarifaDia();
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
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

	public int getCupoDisponible() {
		return cupoDisponible;
	}

	public void setCupoDisponible(int cupoDisponible) {
		this.cupoDisponible = cupoDisponible;
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

	public int getCupoMaximo() {
		return cupoMaximo;
	}

	public void setCupoMaximo(int cupoMaximo) {
		this.cupoMaximo = cupoMaximo;
	}
	
}
