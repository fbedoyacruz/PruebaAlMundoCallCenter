package com.co.almundo.prueba.callcenter.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.co.almundo.prueba.callcenter.controller.Dispatcher;
import com.co.almundo.prueba.callcenter.model.Call;

/**
 * Simulador de llamadas 
 * @author fernbecr
 */
@Component
public class CallSimulator {
	
	/*Instancia del logguer*/
	private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);
	
	/**
	 * Metodo encargado de simular una llamada cada 1 segundos
	 */
	@Scheduled(fixedDelay = 1000)
    public void generarLLamada() {	
		LOGGER.info("----------------------------SIMULADOR DE LLAMADA ----------------------------");
		Call llamada = new Call();
		CallingQueue.glueCall(llamada);
		LOGGER.info("---------------------------- LLAMADA FUEN PUESTA EN ESPERA Y SE HA ENCCOLADO ----------------------------");
		LOGGER.info("---------------------------- LLAMADAS EN ESPERA EN LA COLA: " + CallingQueue.callsQueue()+"----------------------------");
		
    }
}
