package com.estacionamiento.service.impl;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estacionamiento.commons.util.EstacionamientoUtil;
import com.estacionamiento.entity.FacturaParqueoEntity;
import com.estacionamiento.exception.EstacionamientoException;
import com.estacionamiento.model.FacturaParqueo;
import com.estacionamiento.model.FacturaParqueoCarro;
import com.estacionamiento.model.FacturaParqueoMoto;
import com.estacionamiento.model.PeticionServicioParqueo;
import com.estacionamiento.model.ServicioParqueo;
import com.estacionamiento.repository.FacturaParqueoRepository;
import com.estacionamiento.repository.ServicioParqueoRepository;
import com.estacionamiento.service.IEstacionamientoService;

@Service
public class EstacionamientoServiceImpl implements IEstacionamientoService{
	
	@Autowired
	ServicioParqueoRepository servicioParqueoRepository;
	
	@Autowired
	FacturaParqueoRepository facturaParqueoRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstacionamientoServiceImpl.class);

	@Override
	@Transactional
	public FacturaParqueoEntity registrarEntradaEstacionamiento(ServicioParqueo servicioParqueo) throws EstacionamientoException{
		
		FacturaParqueoEntity facturaParqueoEntity = new FacturaParqueoEntity();
		try{
			
			servicioParqueo = this.comprobarDisponibilidadParqueo((null != servicioParqueo && null!=servicioParqueo.getPeticionServicioParqueo()) ? servicioParqueo.getPeticionServicioParqueo() : null);
			if(null==servicioParqueo.getError()){
				
				facturaParqueoEntity = this.crearFactura(servicioParqueo);
				facturaParqueoRepository.save(facturaParqueoEntity);
				servicioParqueoRepository.descontarCupoDisponible(servicioParqueo.getId());
			}
		}catch(EstacionamientoException e){
			
			facturaParqueoEntity.setError(e.getMessage());
			LOGGER.error(EstacionamientoUtil.ERROR_METODO_REGISTRO_FACTURA,e);
		}
		
		return facturaParqueoEntity;
	}
	
	private ServicioParqueo comprobarDisponibilidadParqueo(PeticionServicioParqueo peticionServicioParqueo) {
		
		ServicioParqueo servicioParqueo = new ServicioParqueo();
		try{
			
			if(null!=peticionServicioParqueo && peticionServicioParqueo.getTipoVehiculo()>0){
					
				servicioParqueo = this.comprobarCupoDisponible(peticionServicioParqueo.getTipoVehiculo());
				
				this.validarRestriccionPlacaVehiculo(peticionServicioParqueo);
				
				servicioParqueo.setPeticionServicioParqueo(peticionServicioParqueo);
			}
		}catch(EstacionamientoException e){
			
			servicioParqueo.setError(e.getMessage());
			LOGGER.error(EstacionamientoUtil.ERROR_METODO_COMPROBAR_DISPONIBILIDAD,e);			
		}
		return servicioParqueo;
	}
	
	private ServicioParqueo comprobarCupoDisponible(int codigo) throws EstacionamientoException{
		
		ServicioParqueo servicioParqueo = new ServicioParqueo(servicioParqueoRepository.findByCodigo(codigo));
		if(servicioParqueo.getCupoDisponible() == 0){
			
			throw new EstacionamientoException(EstacionamientoUtil.SIN_CUPO);
		}
		return servicioParqueo;
	}
	
	private void validarRestriccionPlacaVehiculo(PeticionServicioParqueo peticionServicioParqueo) throws EstacionamientoException{
		
		if(EstacionamientoUtil.placaVehiculoIniciaPorA(peticionServicioParqueo.getPlacaVehiculo()) 
				&& !EstacionamientoUtil.esDomingoOLunes(peticionServicioParqueo.getFecha())){
				
			throw new EstacionamientoException(EstacionamientoUtil.PLACA_NO_AUTORIZADA);			
		}
		
	}
	
	private FacturaParqueoEntity crearFactura(ServicioParqueo servicioParqueo) throws EstacionamientoException{
		
		FacturaParqueoEntity facturaParqueoEntity = null;
		FacturaParqueo facturaParqueo = null;
		PeticionServicioParqueo peticionServicioParqueo = servicioParqueo.getPeticionServicioParqueo();
		if(null!=peticionServicioParqueo){
			
			if(null!=peticionServicioParqueo.getPlacaVehiculo() && null == peticionServicioParqueo.getCilindrajeMoto()){
				
				facturaParqueo = new FacturaParqueoCarro(-1, peticionServicioParqueo.getPlacaVehiculo(), Calendar.getInstance(), null, EstacionamientoUtil.ESTADO_PENDIENTE, 0, servicioParqueo);
			}else if(null!=peticionServicioParqueo.getPlacaVehiculo() && null != peticionServicioParqueo.getCilindrajeMoto()){
				
				facturaParqueo = new FacturaParqueoMoto(-1, peticionServicioParqueo.getPlacaVehiculo(), Integer.parseInt(peticionServicioParqueo.getCilindrajeMoto()), Calendar.getInstance(), null, EstacionamientoUtil.ESTADO_PENDIENTE, servicioParqueo);
			}else{
				throw new EstacionamientoException(EstacionamientoUtil.ERROR_CREANDO_FACTURA);
			}
		}
		
		if (facturaParqueo != null) {
			
			facturaParqueoEntity = new FacturaParqueoEntity(facturaParqueo);
		}
		return facturaParqueoEntity;
	}

}
