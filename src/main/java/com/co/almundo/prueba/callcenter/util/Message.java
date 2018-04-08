package com.co.almundo.prueba.callcenter.util;

import org.springframework.context.ApplicationEvent;

import com.co.almundo.prueba.callcenter.model.Employee;

/**
 * Event para notificar la disponibilidad de un empleado
 * @author fernbecr
 *
 */
public class Message extends ApplicationEvent{
	
	private static final long serialVersionUID = 1L;
	private Employee employee;
	
	public Message(Object source, Employee employee) {
		super(source);
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}	
}
