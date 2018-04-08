package com.co.almundo.prueba.callcenter.business;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.co.almundo.prueba.callcenter.enums.EmployeeStatus;
import com.co.almundo.prueba.callcenter.enums.CallState;
import com.co.almundo.prueba.callcenter.model.Employee;
import com.co.almundo.prueba.callcenter.model.EmployeeCall;
import com.co.almundo.prueba.callcenter.model.Call;
import com.co.almundo.prueba.callcenter.service.CallService;
import com.co.almundo.prueba.callcenter.util.Message;

/**
 * Clase encargada de realizar el procesamiento de las llamadas
 * @author fernbecr
 *
 */
@Component
@Scope("prototype")
public class CallExecutor implements Runnable{
	
	/*Instancia del logguer*/
	private static final Logger LOGGER = LoggerFactory.getLogger(CallExecutor.class);	
	public final static int MILISECONDS = 1000;
	
	/*Inyección de dependencias service y EvenT Publisher*/	 
	@Autowired
    private CallService checkAsyncService;
	
	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;
		
	/*Entidad necesaria para el procesamiento de las llamadas*/
	private EmployeeCall employeeCall;

	/**
	 * Metodo que procesa la llamada asignada al empleado disponible
	 * aqui se realiza la simulacion de la duracion de la llamada y
	 * se finaliza el proceso
	 */
	@Override
	public void run() {
		try {
			Call callCurrent = employeeCall.getCall();
			Employee employee = employeeCall.getEmployee();
			/*
			 * Actualiza llamada a LLAMADA_EN_PROGRESO
			 * Calcula la duracion de la llamada
			 */
			Long idLlamada = callCurrent.getId();
			LOGGER.info("----------------------------  LA LLAMADA : " + idLlamada + " FUE ASIGNADA AL EMPLEADO: " + employeeCall.getEmployee().getName() +"----------------------------");
			employee.setState(EmployeeStatus.OCUPADO);
			checkAsyncService.updateStatusEmployee(employee);
			
			LOGGER.info("----------------------------  ACTUALIZANDO LLAMADA : " + idLlamada + " A LLAMADA EN PROGRESO ---------------------------- ");
			callCurrent.setState(CallState.LLAMADA_EN_PROGRESO);
			checkAsyncService.updateStateCall(callCurrent);

			//Se simula el tiempo de la llamada en el proceso
			Thread.sleep(employeeCall.getDuration() * MILISECONDS);
			LOGGER.info("----------------------------  REGISTRANDO LLAMADA "+ idLlamada +" AL EMPLEADO ---------------------------- ");
			checkAsyncService.assignedCallEmployee(employeeCall);

			/*
			 * Actualiza el estado de la llamada a FINALIZADA
			 */
			LOGGER.info("---------------------------- ACTUALIZANDO LLAMADA : " + idLlamada + " A LLAMADA FINALIZADA ----------------------------");
			callCurrent.setState(CallState.LLAMADA_FINALIZADA);
			checkAsyncService.updateStateCall(employeeCall.getCall());
			
			LOGGER.info("---------------------------- LA LLAMADA : " +idLlamada + " A FINALIZADO LA ATENDIO EL EMPLEADO : " + employee.getName()+" ---------------------------- ");
			employee.setState(EmployeeStatus.DISPONIBLE);
			checkAsyncService.updateStatusEmployee(employee);
			applicationEventPublisher.publishEvent(new Message(this, employee));
			
		} catch (InterruptedException e) {
			LOGGER.error("---------------------------- ERROR PROCESANDO LA LLAMADA: " + employeeCall.getCall().getId()+" ---------------------------- ");
		}		
	}

	/*
	 * Métodos geter y setter de la entida EmpleadoLlamada	 
	 */
	
	public EmployeeCall getEmployeeCall() {
		return employeeCall;
	}

	public void employeeCall(EmployeeCall empleadoLlamada) {
		this.employeeCall = empleadoLlamada;
	}	
}
