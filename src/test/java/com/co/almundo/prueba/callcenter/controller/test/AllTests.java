package com.co.almundo.prueba.callcenter.controller.test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.almundo.prueba.callcenter.business.CallingQueue;
import com.co.almundo.prueba.callcenter.config.test.TestApplicationConfiguration;
import com.co.almundo.prueba.callcenter.controller.Dispatcher;
import com.co.almundo.prueba.callcenter.enums.CallState;
import com.co.almundo.prueba.callcenter.enums.EmployeeStatus;
import com.co.almundo.prueba.callcenter.enums.TypeEmployee;
import com.co.almundo.prueba.callcenter.model.Call;
import com.co.almundo.prueba.callcenter.model.Employee;
import com.co.almundo.prueba.callcenter.persistence.CallBean;
import com.co.almundo.prueba.callcenter.persistence.EmployeeBean;
import com.co.almundo.prueba.callcenter.testUnit.CallTest;

import junit.framework.TestSuite;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
@TestPropertySource(properties = "app.scheduling.enable=false")
public class AllTests extends TestSuite  {

	public static final int CERO = 0;

	@Autowired(required=true)
    private Dispatcher dispatcher;
	
	@Autowired(required=true)
    private EmployeeBean employeeBean;
	
	@Autowired(required=true)
    private CallBean callBean;	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AllTests.class);	
	private ExecutorService executor = Executors.newFixedThreadPool(1);
	
	@Test
	public void llamdasConcurrentes() throws Exception {
		
		//Se contruyen 10 llamadas temporales y se encolan 
		int call = 10;
		for (int i = 1; i <= call; i++) {
			CallingQueue.glueCall(new CallTest().byIdEmployee((long) i ).build());
		}
		
		//Se envía tru al runing paa que comience el proceso de llamadas
		dispatcher.setRunning(true);
		executor.execute(dispatcher);
		
		//Se verifica si fue capaz de desencolar las 10 llamadas
		//se pausa el hilo para que las llamadas leguen al tiempo
		Thread.sleep(10000);
		dispatcher.setRunning(false);
		Assert.assertTrue(CallingQueue.callsQueue() == CERO);
		
	}

	@Test
	public void insertEmployee() throws Exception {
		
		ArrayList<Employee>listEmployeeInsert = new ArrayList<>();		
		for(long i = 50; i< 80; i++) {
			Employee employee = new Employee();
			employee.setId(i);
			employee.setEmail("email"+i+"@gmil.com");
			employee.setCharge(TypeEmployee.OPERADOR);
			employee.setState(EmployeeStatus.DISPONIBLE);			
			
			listEmployeeInsert.add(employee);
		}
		
		for(long i = 80; i< 90; i++) {
			Employee employee = new Employee();
			employee.setId(i);
			employee.setEmail("email"+i+"@gmil.com");
			employee.setCharge(TypeEmployee.SUPERVISOR);
			employee.setState(EmployeeStatus.DISPONIBLE);			
			
			listEmployeeInsert.add(employee);
		}
		
		for(long i = 90; i< 100; i++) {
			Employee employee = new Employee();
			employee.setId(i);
			employee.setEmail("email"+i+"@gmil.com");
			employee.setCharge(TypeEmployee.DIRECTOR);
			employee.setState(EmployeeStatus.DISPONIBLE);			
			
			listEmployeeInsert.add(employee);
		}
		
		employeeBean.saveAll(listEmployeeInsert);
		listEmployeeInsert.clear();
		
		listEmployeeInsert = (ArrayList<Employee>) employeeBean.findAll();
		
		if(listEmployeeInsert != null && !listEmployeeInsert.isEmpty()) {
			
			for(Employee employee : listEmployeeInsert) {
				LOGGER.info("----------------- SE CONSULTA EL USUARIO "+ employee.getName());
			}
		}
		
	}
	
	@Test
	public void insertCall() throws Exception {
		ArrayList<Call> listCall = new ArrayList<>();
		for(long i  = 9000; i<1000; i++) {			
			Call call = new  Call();
			call.setId(i);
			call.setState(CallState.LLAMADA_EN_COLA);
			listCall.add(call);		
		}
		
		callBean.saveAll(listCall);
		listCall.clear();
		listCall = (ArrayList<Call>) callBean.findAll();
		
		if(listCall != null && !listCall.isEmpty()) {
			for(Call call : listCall) {
				LOGGER.info("----------------- SE CONSULTA LA LAMADA "+ call.getId());
			}
		}
	}		
}
