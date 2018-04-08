package com.co.almundo.prueba.callcenter.business;

import java.util.LinkedList;
import java.util.Queue;

import com.co.almundo.prueba.callcenter.model.Call;

/**
 * Clase encargada de Almacenar las llamadas en una cola
 * para su posterior procesamiento
 * @author Ferney Bedoya
 */
public class CallingQueue {
	
	/*variables estaticas*/
	private static CallingQueue instance;
	
	/*Declaraciónde la cola para la llamada*/
	private Queue<Call> callQueue;
	
	/**
	 * Conversión de la clase CallingQueue en un patron de diseño singleton	 
	 */
	public static CallingQueue getInstance() {
		if(instance == null) {
			instance = new CallingQueue();
		}
		return instance;
	}
	
	/**
	 * Se crea la cola de llamadas para mantener el orden de ingreso
	 */
	private CallingQueue() {
		this.callQueue = new LinkedList<>();
	}

	/**
	 * Metodo encargado de obtener de la lista de llamadas actuales en la cola
	 * para su procesamiento
	 *  @return Lista de llamadas encoladas
	 */	
	public static Call listCallInQueue(){
        return getInstance().callQueue.poll();
    }
	
	/**
	 * Método encargado de devolver la cantida de llamada encoladas
	 * @return cantidad de llamadas encoladas
	 */
	public static int callsQueue() {
		return getInstance().callQueue.size();
	}
	
	/**
	 * Metodo creado para encolar las llamadas en una lista de espera
	 * @param call
	 */
	public static void glueCall(Call call){
		getInstance().callQueue.add(call);
    }		
}
