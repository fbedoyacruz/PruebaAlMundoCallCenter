package com.co.almundo.prueba.callcenter.util;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Calse necesaria para el arranque de las tareas automaticas
 * inicio de llamaadas
 * @author fernbecr
 */
@Configuration
@ConditionalOnProperty(value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true)
@EnableScheduling
public class ScheduleConfig {
}
