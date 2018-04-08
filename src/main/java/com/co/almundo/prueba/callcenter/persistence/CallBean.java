package com.co.almundo.prueba.callcenter.persistence;

import org.springframework.data.repository.CrudRepository;

import com.co.almundo.prueba.callcenter.model.Call;

/**
 * Interfaz que extiende de CrudRepositorypara aprovechar las propiedades 
 * de Sping con respecto a las operaciones CRUD No es necesario implementar 
 * métodos propios solo consumir los del CRUD
 * @author fernbecr
 */
public interface CallBean extends CrudRepository<Call, Long>{
}
