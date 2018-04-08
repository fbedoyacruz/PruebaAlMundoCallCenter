package com.co.almundo.prueba.callcenter.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad empleado llamada (EmployeeCall) 
 * @author fernbecr
 *
 */
@Entity
@Table(name = "am_empleado_llamada", schema="ALMUNDO_CALLCENTER")
public class EmployeeCall {
	
	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "empleado_id")
	private Employee employee;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "llamada_id")
	private Call call;
	
	private Long duration;
	
	@Temporal(TemporalType.DATE)
	private Date date;
		
	public EmployeeCall() {
	}

	public EmployeeCall(Employee employee, Call call, Long duration) {
		this.employee = employee;
		this.call = call;
		this.duration = duration;
		this.date = new Date();
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
	
	public Employee getEmployee() {
		return employee;
	}
	
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Call getCall() {
		return call;
	}
	
	public void setCall(Call call) {
		this.call = call;
	}
	
	public Long getDuration() {
		return duration;
	}
	
	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public Date getDate() {
		return date;
	}

	public void setFecha(Date date) {
		this.date = date;
	}	
}
