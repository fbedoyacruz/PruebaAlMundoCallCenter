package com.co.almundo.prueba.callcenter.enums;

/**
 * Enumerador para el estado la llamada, esta solo puede ser 
 * en progreso, en espera, o finalizada 
 * @author fernbecr
 */
public enum CallState {
	
	LLAMADA_EN_PROGRESO,
	LLAMADA_EN_COLA,
	LLAMADA_FINALIZADA;
	
	
	/**
	 * Metodo para obtener los valores del enumerador
	 * @param name
	 * @return enum
	 */
	public static CallState getEnumName(String name) {
		for(CallState callState : CallState.values()) {
			if(callState.name().equals(name)) {
				
				return callState;
			}
		}
		
		return null;
	}
}
