package com.estacionamiento.bussines;

import java.util.Calendar;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.entity.VehiculoParqueoEntity;
import com.estacionamiento.entity.ServicioParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.VehiculoParqueo;
import com.estacionamiento.model.VehiculoParqueoCarro;
import com.estacionamiento.model.VehiculoParqueoMoto;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.service.EstacionamientoService;

@Service
public class EstacionamientoBussines {
	
	@Autowired
	EstacionamientoService estacionamientoService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstacionamientoBussines.class);

	@Transactional
	public VehiculoParqueo registrarEntradaEstacionamiento(PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException {
		
		VehiculoParqueo vehiculoParqueo = new VehiculoParqueo();
		ServicioParqueo servicioParqueo = null;
		try{
			
			VehiculoParqueoEntity vehiculoParqueoExiste = estacionamientoService.findVehiculoParqueoByPlacaByEstado(peticionServicioParqueo.getPlacaVehiculo(), EstacionamientoUtil.ESTADO_PENDIENTE);
			if(null == vehiculoParqueoExiste){
				
				servicioParqueo = this.comprobarDisponibilidadParqueo(peticionServicioParqueo);
				if(null!= servicioParqueo.getPeticionServicioParqueo() && null==servicioParqueo.getError()){
					
					vehiculoParqueo = this.inicializarVehiculoParqueo(servicioParqueo);
					
					estacionamientoService.registrarEntradaVehiculoParqueo(vehiculoParqueo, servicioParqueo);
				}else{
				
					vehiculoParqueo.setError(servicioParqueo.getError());
				}
			}else{
				
				vehiculoParqueo.setError(EstacionamientoUtil.EXISTE_VEHICULO);
			}
		}catch(EstacionamientoException e){
			
			LOGGER.error(EstacionamientoUtil.ERROR_METODO_REGISTRO_FACTURA,e);
		}
		
		return vehiculoParqueo;
	}
	
	public ServicioParqueo comprobarDisponibilidadParqueo(PeticionServicioParqueo peticionServicioParqueo) {
		
		ServicioParqueo servicioParqueo = new ServicioParqueo();
		try{
			
			if(null != peticionServicioParqueo && peticionServicioParqueo.getTipoVehiculo()>0){
					
				servicioParqueo = this.comprobarCupoDisponible(peticionServicioParqueo.getTipoVehiculo());
				if(null!=servicioParqueo && null==servicioParqueo.getError()){
					
					this.validarRestriccionPlacaVehiculo(peticionServicioParqueo);
					
					servicioParqueo.setPeticionServicioParqueo(peticionServicioParqueo);
				}
			}
		}catch(EstacionamientoException e){
			
			servicioParqueo.setError(e.getMessage());
			LOGGER.error(EstacionamientoUtil.ERROR_METODO_COMPROBAR_DISPONIBILIDAD,e);			
		}
		return servicioParqueo;
	}
	
	public ServicioParqueo comprobarCupoDisponible(int codigo) throws EstacionamientoException {
		
		ServicioParqueo servicioParqueo = null;
		ServicioParqueoEntity servicioParqueoEntity = estacionamientoService.findServicioParqueoByCodigo(codigo);
		if(null==servicioParqueoEntity){
			
			throw new EstacionamientoException("No se encontraron datos en la tabla ServicioParqueo.");
		}
			
		servicioParqueo = new ServicioParqueo(servicioParqueoEntity);
		if(servicioParqueo.getCupoDisponible() == 0){
			
			servicioParqueo.setError(EstacionamientoUtil.SIN_CUPO);
		}
		return servicioParqueo;
	}
	
	public void validarRestriccionPlacaVehiculo(PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException {
		
		if(EstacionamientoUtil.placaVehiculoIniciaPorA(peticionServicioParqueo.getPlacaVehiculo()) 
				&& !EstacionamientoUtil.esDomingoOLunes(peticionServicioParqueo.getFecha())){
				
			throw new EstacionamientoException(EstacionamientoUtil.PLACA_NO_AUTORIZADA);			
		}
		
	}
	
	public VehiculoParqueo inicializarVehiculoParqueo(ServicioParqueo servicioParqueo) throws EstacionamientoException{
		
		VehiculoParqueo vehiculoParqueo = null;
		PeticionServicioParqueo peticionServicioParqueo = servicioParqueo.getPeticionServicioParqueo();
		if(null!=peticionServicioParqueo){
			
			if(null!=peticionServicioParqueo.getPlacaVehiculo() && null == peticionServicioParqueo.getCilindrajeMoto()){
				
				vehiculoParqueo = new VehiculoParqueoCarro(-1, peticionServicioParqueo.getPlacaVehiculo(), Calendar.getInstance(), null, EstacionamientoUtil.ESTADO_PENDIENTE, 0, servicioParqueo);
			}else if(null!=peticionServicioParqueo.getPlacaVehiculo() && null != peticionServicioParqueo.getCilindrajeMoto()){
				
				vehiculoParqueo = new VehiculoParqueoMoto(-1, peticionServicioParqueo.getPlacaVehiculo(), Integer.parseInt(peticionServicioParqueo.getCilindrajeMoto()), Calendar.getInstance(), null, EstacionamientoUtil.ESTADO_PENDIENTE, servicioParqueo);
			}else{
				
				throw new EstacionamientoException(EstacionamientoUtil.ERROR_CREANDO_FACTURA);
			}
		}
		
		return vehiculoParqueo;
	}

	@Transactional
	public VehiculoParqueo registrarSalidaEstacionamiento(long idFactura) throws EstacionamientoException {
		
		VehiculoParqueo vehiculoParqueo = null;
		VehiculoParqueoEntity vehiculoParqueoEntity = estacionamientoService.findVehiculoParqueoById(idFactura);
		if(null!=vehiculoParqueoEntity){

			vehiculoParqueo = vehiculoParqueoEntity.getCilindrajeMoto() > 0 ? new VehiculoParqueoMoto(vehiculoParqueoEntity) : new VehiculoParqueoCarro(vehiculoParqueoEntity);
			vehiculoParqueo.calcularValorServicioParqueo();
			vehiculoParqueoEntity.setFechaSalida(Calendar.getInstance());
			vehiculoParqueoEntity.setEstado(EstacionamientoUtil.ESTADO_PAGADO);
			vehiculoParqueoEntity.setTiempoServicio(vehiculoParqueo.getTiempoServicioHoras());
			vehiculoParqueoEntity.setValorServicio(vehiculoParqueo.getValorServicio());
			estacionamientoService.registrarSalidaVehiculoParqueo(vehiculoParqueoEntity);
		}
		
		return vehiculoParqueo;
	}

	public List<VehiculoParqueoEntity> consultarVehiculos() {
		
		return estacionamientoService.findAllVehiculosParqueados();
	}

}
