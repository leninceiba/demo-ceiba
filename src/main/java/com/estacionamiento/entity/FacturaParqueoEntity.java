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

import com.estacionamiento.model.FacturaParqueo;
import com.estacionamiento.model.FacturaParqueoCarro;
import com.estacionamiento.model.FacturaParqueoMoto;

@Entity
@Table(name="factura_parqueo")
public class FacturaParqueoEntity implements Serializable{
	
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
    
	public FacturaParqueoEntity() {
		
	}

	public FacturaParqueoEntity(FacturaParqueo facturaParqueo) {
		super();
		this.fechaEntrada = facturaParqueo.getFechaEntrada();
		this.fechaSalida = facturaParqueo.getFechaSalida();
		this.estado = facturaParqueo.getEstado();
		this.valorServicio = facturaParqueo.getValorServicio();
		this.tiempoServicio = facturaParqueo.getTiempoServicioHoras();
		
		if(facturaParqueo instanceof FacturaParqueoCarro){
			FacturaParqueoCarro facturaParqueoCarro = (FacturaParqueoCarro)facturaParqueo;
			this.placaVehiculo = facturaParqueoCarro.getPlaca();
		}
		
		if(facturaParqueo instanceof FacturaParqueoMoto){
			FacturaParqueoMoto facturaParqueoMoto = (FacturaParqueoMoto)facturaParqueo;
			this.placaVehiculo = facturaParqueoMoto.getPlaca();
			this.cilindrajeMoto = facturaParqueoMoto.getCilindraje();
		}
		this.servicioParqueo = new ServicioParqueoEntity(facturaParqueo.getServicioParqueo());
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
