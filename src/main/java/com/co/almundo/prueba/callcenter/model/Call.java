package com.co.almundo.prueba.callcenter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.co.almundo.prueba.callcenter.enums.CallState;

/**
 * Entidad llamada (Call)
 * @author fernbecr
 *
 */
@Entity
@Table(name = "am_llamada", schema="SPRING_DATA_JPA_EXAMPLE")
public class Call {
	
	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name="llamada_generator", sequenceName = "llamada_seq", allocationSize=1)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private CallState state;
	
	public Call() {
		this.state = CallState.LLAMADA_EN_COLA;
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

	public CallState getState() {
		return state;
	}

	public void setState(CallState state) {
		this.state = state;
	}	
}
