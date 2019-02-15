package com.estacionamiento.entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.model.VehiculoParqueoCarro;
import com.estacionamiento.model.VehiculoParqueoMoto;

@Entity
@Table(name="vehiculo_parqueo")
public class VehiculoParqueoEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private Calendar fechaEntrada;
	private Calendar fechaSalida;	
	private String estado;
    private long valorServicio;
    private long tiempoServicio;
    private String placaVehiculo;
    private int cilindrajeMoto;   
    @ManyToOne(fetch = FetchType.EAGER)    
    private ServicioParqueoEntity servicioParqueo;
    private String error;
    
	public VehiculoParqueoEntity() {
		
	}

	public VehiculoParqueoEntity(VehiculoParqueo vehiculoParqueo) {
		super();
		this.fechaEntrada = vehiculoParqueo.getFechaEntrada();
		this.fechaSalida = vehiculoParqueo.getFechaSalida();
		this.estado = vehiculoParqueo.getEstado();
		this.valorServicio = vehiculoParqueo.getValorServicio();
		this.tiempoServicio = vehiculoParqueo.getTiempoServicioHoras();
		
		if(vehiculoParqueo instanceof VehiculoParqueoCarro){
			VehiculoParqueoCarro vehiculoParqueoCarro = (VehiculoParqueoCarro)vehiculoParqueo;
			this.placaVehiculo = vehiculoParqueoCarro.getPlaca();
		}
		
		if(vehiculoParqueo instanceof VehiculoParqueoMoto){
			VehiculoParqueoMoto vehiculoParqueoMoto = (VehiculoParqueoMoto)vehiculoParqueo;
			this.placaVehiculo = vehiculoParqueoMoto.getPlaca();
			this.cilindrajeMoto = vehiculoParqueoMoto.getCilindraje();
		}
		this.servicioParqueo = new ServicioParqueoEntity(vehiculoParqueo.getServicioParqueo());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Calendar getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(Calendar fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public Calendar getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(Calendar fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public long getValorServicio() {
		return valorServicio;
	}

	public void setValorServicio(long valorServicio) {
		this.valorServicio = valorServicio;
	}

	public long getTiempoServicio() {
		return tiempoServicio;
	}

	public void setTiempoServicio(long tiempoServicio) {
		this.tiempoServicio = tiempoServicio;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public int getCilindrajeMoto() {
		return cilindrajeMoto;
	}

	public void setCilindrajeMoto(int cilindrajeMoto) {
		this.cilindrajeMoto = cilindrajeMoto;
	}

	public ServicioParqueoEntity getServicioParqueo() {
		return servicioParqueo;
	}

	public void setServicioParqueo(ServicioParqueoEntity servicioParqueo) {
		this.servicioParqueo = servicioParqueo;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}	

}
