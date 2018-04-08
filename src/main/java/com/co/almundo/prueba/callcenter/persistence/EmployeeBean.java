package com.co.almundo.prueba.callcenter.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.co.almundo.prueba.callcenter.model.Employee;

/**
 * Interfaz que extiende de CrudRepositorypara aprovechar las propiedades 
 * de Sping con respecto a las operaciones CRUD y donde se implementan métodos propios
 * con lenguaje JPQL
 * @author fernbecr
 */
public interface EmployeeBean extends CrudRepository<Employee, Long>  {
	
	@Query("SELECT e FROM Employee e ")
	List<Employee> listEmployee();
	
	@Query("SELECT e FROM Employee e where e.state = 'OCUPADO'")
	List<Employee> listEmployeBusy();
	
	@Query("SELECT e FROM Employee e where e.state = 'DISPONIBLE'")
	List<Employee> listEmployeeAvailable();	
}
