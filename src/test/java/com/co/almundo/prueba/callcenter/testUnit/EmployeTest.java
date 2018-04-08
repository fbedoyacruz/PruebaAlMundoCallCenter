package com.co.almundo.prueba.callcenter.testUnit;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.co.almundo.prueba.callcenter.enums.EmployeeStatus;
import com.co.almundo.prueba.callcenter.enums.TypeEmployee;
import com.co.almundo.prueba.callcenter.model.Employee;

import junit.framework.TestCase;

public class EmployeTest extends TestCase{

	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	private String email;
	
	@Enumerated(EnumType.STRING)
	private EmployeeStatus state;
	
	@Enumerated(EnumType.STRING)
	private TypeEmployee charge;
	
	public EmployeTest(){	
	}
	
	public EmployeTest(EmployeeStatus state, TypeEmployee charge) {
		this.state = state;
		this.charge = charge;
	}
	
	/**
	 * Inicio de los métodos setters y getters
	 */	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
		
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public EmployeeStatus getState() {
		return state;
	}
	
	public void setState(EmployeeStatus state) {
		this.state = state;
	}
	
	public TypeEmployee getCharge() {
		return charge;
	}
	
	public void setCharge(TypeEmployee charge) {
		this.charge = charge;
	}	
}
