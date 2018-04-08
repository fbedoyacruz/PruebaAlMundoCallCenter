package com.co.almundo.prueba.callcenter.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import com.co.almundo.prueba.callcenter.business.CallExecutor;
import com.co.almundo.prueba.callcenter.model.Employee;
import com.co.almundo.prueba.callcenter.model.EmployeeCall;
import com.co.almundo.prueba.callcenter.model.Call;

/**
 * Clase que procesa las operaciones de negocio
 * @author fernbecr
 *
 */
@Service
@Transactional
public class CallService {
	
	/*Inyección de dependencias*/
	@Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private ApplicationContext applicationContext;
    
    //instancia del entity manager
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * Método encargado de invocar el procesador de llamadas
     * @param employeCall
     */
    public void processCall(EmployeeCall employeCall) {
		CallExecutor processor = applicationContext.getBean(CallExecutor.class);
		processor.employeeCall(employeCall);
		taskExecutor.execute(processor);
    }
    
    /**
     * Asigna la llamada a un empleado
     * @param employeeCall
     */
    public void assignedCallEmployee(EmployeeCall employeeCall) {
    	entityManager.persist(employeeCall);    	
    }
   
   /**
    * Actualización del estado de una llamada 
    * @param llamada
    */
    public void updateStateCall(Call call){
    		entityManager.merge(call); 		
    }
    
    /**
     * Atualización del estado de un empleado 
     * @param employee
     */
    public void updateStatusEmployee(Employee employee) {
    	entityManager.merge(employee);
    }
}
