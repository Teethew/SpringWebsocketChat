package com.example.websocketdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	/*
	 * A anotação @EnableWebSocketMessageBroker é usada para habilitar nosso servidor WebSocket. 
     * Implementamos a interface WebSocketMessageBrokerConfigurer e implementamos alguns de seus métodos para configurar a conexão websocket.
     */
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").withSockJS();
        
		/*
		 * No primeiro método, registramos um endpoint do websocket que o client vai usar para se conectar com o nosso servidor websocket.
		 *
		 * Note o uso de withSockJS() com a configuração do endpoint. 
		 * SockJS é usado para habilitar opções de fallback para browsers que não suportam websocket.
		 *
		 * Talvez você tenha notado a palavra STOMP no nome do método. 
		 * Esses métodos vem da implementação STOMP do Spring. STOMP significa Simple Text Oriented Messaging Protocol (Protocolo de mensagens orientado a textos simples). 
		 * É um protocolo de mensagens que define o formato e regras para a transferência de dados.
		 *
		 * Por que precisamos do STOMP? Bom, WebSocket é apenas um protocolo de comunicação. 
		 * Ele não define coisas como: Como mandar uma mensagem apenas para usuários que estão inscritos em um tópico específico, ou como mandar uma mensagem para um usuário particular. 
		 * Precisamos do STOMP para essas funcionalidades.
		 */
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
        
        /* No segundo método, configuramos um message broker que vai ser usado para direcionar mensagens de um client para outro.
		 *
         * A primeira linha define que mensagens cujo destino comecem com "/app" devem ser enviadas para métodos de message-handling.
		 *
		 * E, a segunda linha define que as mensagens cujo destino comecem com “/topic” devem ser enviadas para o message broker. 
		 * O message broker transmite mensagens para todos os clients conectados que estão inscritos em um tópico específico.
		 */
    }
    
    
}
