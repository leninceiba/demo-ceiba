package com.estacionamiento.service.impl;

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
import com.estacionamiento.repository.VehiculoParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;
import com.estacionamiento.service.IEstacionamientoService;

@Service
public class EstacionamientoServiceImpl implements IEstacionamientoService{
	
	@Autowired
	ServicioParqueoRepository servicioParqueoRepository;
	
	@Autowired
	VehiculoParqueoRepository vehiculoParqueoRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstacionamientoServiceImpl.class);

	@Override
	@Transactional
	public VehiculoParqueoEntity registrarEntradaEstacionamiento(PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException{
		
		VehiculoParqueoEntity vehiculoParqueoEntity = new VehiculoParqueoEntity();
		ServicioParqueo servicioParqueo = null;
		try{
			
			VehiculoParqueoEntity facturaParqueoExiste = this.existeVehiculoParqueado(peticionServicioParqueo);
			if(null == facturaParqueoExiste){
				
				servicioParqueo = this.comprobarDisponibilidadParqueo(peticionServicioParqueo);
				if(null==servicioParqueo.getError()){
					
					vehiculoParqueoEntity = this.crearVehiculoParqueo(servicioParqueo);
					vehiculoParqueoRepository.save(vehiculoParqueoEntity);
					servicioParqueoRepository.descontarCupoDisponible(servicioParqueo.getId());
				}else{
				
					vehiculoParqueoEntity.setError(servicioParqueo.getError());
				}
			}else{
				
				vehiculoParqueoEntity.setError(EstacionamientoUtil.EXISTE_VEHICULO);
			}
		}catch(EstacionamientoException e){
			
			LOGGER.error(EstacionamientoUtil.ERROR_METODO_REGISTRO_FACTURA,e);
		}
		
		return vehiculoParqueoEntity;
	}
	
	public VehiculoParqueoEntity existeVehiculoParqueado(PeticionServicioParqueo peticionServicioParqueo){
		
		return vehiculoParqueoRepository.findByPlacaVehiculoByEstado(peticionServicioParqueo.getPlacaVehiculo(), EstacionamientoUtil.ESTADO_PENDIENTE);
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
	
	public ServicioParqueo comprobarCupoDisponible(int codigo) throws EstacionamientoException{
		
		ServicioParqueo servicioParqueo = null;
		ServicioParqueoEntity servicioParqueoEntity = servicioParqueoRepository.findByCodigo(codigo);
		if(null==servicioParqueoEntity){
			
			throw new EstacionamientoException("No se encontraron datos en la tabla ServicioParqueo.");
		}
			
		servicioParqueo = new ServicioParqueo(servicioParqueoEntity);
		if(servicioParqueo.getCupoDisponible() == 0){
			
			servicioParqueo.setError(EstacionamientoUtil.SIN_CUPO);
		}
		return servicioParqueo;
	}
	
	public void validarRestriccionPlacaVehiculo(PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException{
		
		if(EstacionamientoUtil.placaVehiculoIniciaPorA(peticionServicioParqueo.getPlacaVehiculo()) 
				&& !EstacionamientoUtil.esDomingoOLunes(peticionServicioParqueo.getFecha())){
				
			throw new EstacionamientoException(EstacionamientoUtil.PLACA_NO_AUTORIZADA);			
		}
		
	}
	
	public VehiculoParqueoEntity crearVehiculoParqueo(ServicioParqueo servicioParqueo) throws EstacionamientoException{
		
		VehiculoParqueoEntity vehiculoParqueoEntity = null;
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
		
		if (vehiculoParqueo != null) {
			
			vehiculoParqueoEntity = new VehiculoParqueoEntity(vehiculoParqueo);
		}
		
		return vehiculoParqueoEntity;
	}

	@Override
	@Transactional
	public VehiculoParqueo registrarSalidaEstacionamiento(long idFactura) throws EstacionamientoException {
		
		VehiculoParqueo vehiculoParqueo = null;
		VehiculoParqueoEntity vehiculoParqueoEntity = vehiculoParqueoRepository.findById(idFactura);
		if(null!=vehiculoParqueoEntity){

			vehiculoParqueo = vehiculoParqueoEntity.getCilindrajeMoto() > 0 ? new VehiculoParqueoMoto(vehiculoParqueoEntity) : new VehiculoParqueoCarro(vehiculoParqueoEntity);
			vehiculoParqueo.calcularValorServicioParqueo();
			vehiculoParqueoEntity.setFechaSalida(Calendar.getInstance());
			vehiculoParqueoEntity.setEstado(EstacionamientoUtil.ESTADO_PAGADO);
			vehiculoParqueoEntity.setTiempoServicio(vehiculoParqueo.getTiempoServicioHoras());
			vehiculoParqueoEntity.setValorServicio(vehiculoParqueo.getValorServicio());
			vehiculoParqueoRepository.save(vehiculoParqueoEntity);
			servicioParqueoRepository.aumentarCupoDisponible(vehiculoParqueoEntity.getServicioParqueo().getId());
		}
		
		return vehiculoParqueo;
	}

	@Override
	public List<VehiculoParqueoEntity> consultarVehiculos() throws EstacionamientoException {
		
		return vehiculoParqueoRepository.findAll();
	}

}
