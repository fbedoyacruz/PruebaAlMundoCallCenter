package com.co.almundo.prueba.callcenter;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.co.almundo.prueba.callcenter.controller.Dispatcher;

/**
 * Clase encargada de realizar el arranque de la aplicación
 * @author Ferney Bedoya
 *
 */
@SpringBootApplication
public class MainApplication implements CommandLineRunner{
	
	/*Inyección del controlador Dispatcher*/
	@Autowired
	private Dispatcher dispatcher;

	@Bean
	//Bean para crear la cola de llamadas
	public JmsListenerContainerFactory<?> myJmsFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		configurer.configure(factory, connectionFactory);
		return factory;
	}

	@Bean
	//Bean para serializar y convertir los mensajes a un objeto json enviados a la cola
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

	/**
	 * Arranque de la aplicación generado con SpringBoot
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);		 
	}
		
    /**    
     * Método run encargado de iniciar las pruebas unitarias y conectar con la capa de negocio 
     * para ejecutar el hilo de la aplicación y dar inicio a las llamadas.
     */
    @Override
    public void run(String... args) throws Exception {
    		dispatcher.setRunning(true);
    		dispatcher.run();
    }	
}
