package com.co.almundo.prueba.callcenter.testUnit;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.co.almundo.prueba.callcenter.enums.CallState;
import com.co.almundo.prueba.callcenter.model.Call;

import junit.framework.TestCase;

@SpringBootApplication
public class CallTest extends TestCase{

	//variables staticas
	private static final Long ID = 1L;
	private static final CallState STATE = CallState.LLAMADA_EN_COLA;

	//Campos de la entidad
	private Long id;
	private CallState state;
	
	public CallTest() {
		this.id = ID;
		this.state = STATE;
	}
	
	public Call build() {
		Call call = new Call();
		call.setId(id);
		call.setState(state);
		
		return call;
	}

	public CallTest byIdEmployee(Long id) {
		this.id = id;
		return this;
	}
}
