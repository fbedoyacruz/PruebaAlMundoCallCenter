package com.co.almundo.prueba.callcenter.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.co.almundo.prueba.callcenter.model.Employee;


/**
 * Clase encargada de recibir la llamada 
 * @author fernbecr
 *
 */
@Component
public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@JmsListener(destination = "user", containerFactory = "myJmsFactory")
	public void receiveMessage(Employee user) {
		LOGGER.info("---------------------------- SE RECIBE LA LLAMADA QUE VIENE DE LA COLA JSM Y SE LE ASIGNA AL EMPLEADO " + user + " ----------------------------");
	}

}