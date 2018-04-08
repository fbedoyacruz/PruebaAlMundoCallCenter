package com.co.almundo.prueba.callcenter.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.co.almundo.prueba.callcenter.business.CallingQueue;
import com.co.almundo.prueba.callcenter.enums.EmployeeStatus;
import com.co.almundo.prueba.callcenter.enums.TypeEmployee;
import com.co.almundo.prueba.callcenter.model.Call;
import com.co.almundo.prueba.callcenter.model.Employee;
import com.co.almundo.prueba.callcenter.model.EmployeeCall;
import com.co.almundo.prueba.callcenter.persistence.CallBean;
import com.co.almundo.prueba.callcenter.persistence.EmployeeBean;
import com.co.almundo.prueba.callcenter.service.CallService;
import com.co.almundo.prueba.callcenter.util.Message;

/**
 * Clase encarga de realizar la lógica de negocio, busqueda de empleados disponibles, 
 * procesamiento de llamadas, asignación de llamadas, puesta de llamada en cola
 * @author fernbecr
 */
@Component
public class Dispatcher implements Runnable, ApplicationListener<Message> {
	
	/*Instancia del logguer*/
	private static final Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);
	
	/*Inyección de dependencias necesarias*/
	@Autowired
    private EmployeeBean employeeBean;
	
	@Autowired
	private CallBean callBean;
	
	@Autowired
    private CallService callService;
	
	//instancia del entity manager
    @PersistenceContext
    private EntityManager entityManager;
	
	/*Recursosnecesarios*/
	private List<Employee> employeesAvailable;	
	private boolean running;
	private static final int MINIMUN_CALL = 5;
	
	/*Randon para la generación aleatoria de los segundos */
	private Random random = new Random();	
	
	/**
	 * Metodo encargado de insertar llamada, calcular la duración de la llamada 
	 * y asignar la llamada a un empleado disponible
	 * @param employee
	 * @param call
	 */
	public void dispatchCall(Employee employee, Call call){

		//Se inserta la llamada 
		callBean.save(call);
		LOGGER.info("---------------------------- LA LLAMADA :" + call.getId()+" HA SIDO ALMACENADA EN BASE DE DATOS ----------------------------");
		
		//Se crea la duración de la llamada en un rango de 5 a 10 segundos
		Long duration = (long) (MINIMUN_CALL + random.nextInt(MINIMUN_CALL));		
		
		//Se le asigna la llamada a un empleado disponible
		EmployeeCall employeeCall = new EmployeeCall(employee, call, duration);
		callService.processCall(employeeCall);
	}

	/**
	 * Metodo run verifica las llamadas que van llegando 
	 * para poder cambiarles el estado 
	 */
	@Override
	public void run() {

		Employee freeEmployee = null;
		Call call = null;
		/*se consultan los empleados disponibles*/
		employeesAvailables();
		
		while (running) {
			try {
				//Se valida si hay empleados libres
				if(!employeesAvailable.isEmpty()) {
					
					call = CallingQueue.listCallInQueue();
					
					if(call != null) {
						try {
							freeEmployee = searchFreePerson();
						} catch (SQLException e) {
							LOGGER.info("---------------------------- ERROR CONSLUTANDO EL LISTADO DE EMPLEADOS ----------------------------");
						}
						//asigna llamada la empleado
						dispatchCall(freeEmployee, call);
					}					
				}else {					
					LOGGER.info("---------------------------- LOS EMPLEADO NO ESTAN DISPONILES, POR FAVO ESPERE ----------------------------");
					Thread.sleep(2000);
				}
			} catch (InterruptedException e) {
				LOGGER.error("---------------------------- ERROR EN LA ASIGNACIÓN DE LA LLAMADA ----------------------------");
			}
		}
	}
	
	/**
	 * Metodo que consulta los empleados disponibles 
	 * @return List<Empleado>, lista de empleados disponibles
	 */
	private void employeesAvailables(){
		employeesAvailable = Collections.synchronizedList(employeeBean.listEmployeeAvailable());
		//employeeBusy();
	}
	
	/**
	 * Método que consulta los empleados ocupados para temas se pruebas
	 */
	private void employeeBusy() {
		ArrayList<Employee> listEmployee = new ArrayList<>();
		listEmployee = (ArrayList<Employee>) Collections.synchronizedList(employeeBean.listEmployeBusy());
		
		if(listEmployee != null && !listEmployee.isEmpty()) {
			
			for(Employee employee : employeesAvailable) {
				LOGGER.info("---------------------------- EL EMPLEADO OCUPADO ES: "+employee.getName()+" ----------------------------");
			}
		}
	}
	
	/**
	 * Metodo encargado de buscar el personal libre
	 * @return Empleado
	 * @throws SQLException 
	 */
	private Employee searchFreePerson() throws SQLException {
		
		Employee employee = null;

		//Se realiza la busqueda de operadores libres como primer filtro
		List<Employee> operators = employeeBean.listEmployee(); 
		
		//Se valida que la consulta listar empleados haya retornado registros
		if(operators != null && !operators.isEmpty()){
			//Se recorre el listado de registros discriminando por Operador, Supervisor y Director
			//respectivamente
			for(Employee freeEmployee: operators) {
				
				if(freeEmployee.getCharge().equals(TypeEmployee.OPERADOR) && freeEmployee.getState().equals(EmployeeStatus.DISPONIBLE)) {
					LOGGER.info("---------------------------- BUSCANDO EMPLEADO OPERADOR ----------------------------");
					employee = freeEmployee;
					this.employeesAvailable.remove(employee);
					LOGGER.info("---------------------------- SE HA ENCONTRADO EL EMPLEADO: "+employee.getName()+" CARGO: "+employee.getCharge()+" ----------------------------");
					break;
				}else if(freeEmployee.getCharge().equals(TypeEmployee.SUPERVISOR) && freeEmployee.getState().equals(EmployeeStatus.DISPONIBLE)) {
					LOGGER.info("---------------------------- BUSCANDO EMPLEADO SUPERVISOR ----------------------------");
					employee = freeEmployee;
					this.employeesAvailable.remove(employee);
					LOGGER.info("---------------------------- SE HA ENCONTRADO EL EMPLEADO: "+employee.getName()+" CARGO: "+employee.getCharge()+" ----------------------------");
					break;
				}else if(freeEmployee.getCharge().equals(TypeEmployee.DIRECTOR) && freeEmployee.getState().equals(EmployeeStatus.DISPONIBLE)) {
					LOGGER.info(" ---------------------------- BUSCANDO DIRECTOR ----------------------------");
					employee = freeEmployee;
					this.employeesAvailable.remove(employee);
					LOGGER.info("---------------------------- SE HA ENCONTRADO EL EMPLEADO: "+employee.getName()+" CARGO: "+employee.getCharge()+" ----------------------------");
					break;
				}
				
			}			
		}
		
		return employee;
	}	

	/**
	 * Evento que se dispara cuando un empleado finaliza una llamada y se
	 * encuentra disponible para recibir otra
	 * 
	 */
	@Override
	public void onApplicationEvent(Message event) {
		this.employeesAvailable.add(event.getEmployee());		
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}

}
