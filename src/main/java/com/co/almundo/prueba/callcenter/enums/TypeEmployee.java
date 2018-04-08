package com.co.almundo.prueba.callcenter.enums;

/**
 * Enumerador para tipo de empleado, por condición solo hay 
 * Operador, Supervisor y Director 
 * @author fernbecr
 */
public enum TypeEmployee {

	OPERADOR,
	SUPERVISOR,
	DIRECTOR;
	
	/**
	 * Metodo para obtener los valores del enumerador
	 * @param name
	 * @return enum
	 */
	public static TypeEmployee getEnumName(String name) {
		for(TypeEmployee typeEmployee : TypeEmployee.values()) {
			if(typeEmployee.name().equals(name)) {
				
				return typeEmployee;
			}
		}
		
		return null;
	}
		
	
}
