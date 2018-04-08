package com.co.almundo.prueba.callcenter.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.co.almundo.prueba.callcenter.enums.EmployeeStatus;
import com.co.almundo.prueba.callcenter.enums.TypeEmployee;
import com.co.almundo.prueba.callcenter.model.Employee;


/**
 * Clase encargada de realizar las llamadas y almacenarlas en una cola JMS
 * repite la llamada cada 5 segundos
 * @author fernbecr
 *
 */
@Component
public class IncomingCall {

	private static final Logger LOGGER = LoggerFactory.getLogger(IncomingCall.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Scheduled(fixedDelay = 5000)
	public void makeCall() {
		LOGGER.info("---------------------------- REALIZANDO LA LLAMADA Y LLAMANDO LA COLA JMS ----------------------------");
		JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
		jmsTemplate.convertAndSend("user", new Employee(EmployeeStatus.DISPONIBLE, TypeEmployee.OPERADOR));
	}

}
