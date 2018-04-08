package com.co.almundo.prueba.callcenter.enums;

/**
 * Enumerador para el estado del empleado, este solo puede estar disponible u/o ocupado
 * @author fernbecr
 */
public enum EmployeeStatus {	
	DISPONIBLE,
	OCUPADO;
	
	/**
	 * Metodo para obtener los valores del enumerador
	 * @param name
	 * @return enum
	 */
	public static EmployeeStatus getEnumName(String name) {
		for(EmployeeStatus employeeStatus : EmployeeStatus.values()) {
			if(employeeStatus.name().equals(name)) {
				
				return employeeStatus;
			}
		}
		
		return null;
	}
}
