package com.example.websocketdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.example.websocketdemo.model.ChatMessage;

@Component
public class WebSocketEventListener {
	
	/*
	 * Usaremos event listeners para receber eventos de conexão e desconexão no socket,
	 * para que possamos registrar esses eventos e transmití-los quando um usuário entrar ou sair da sala de chat
	 */

	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
	
	@EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }
	
	@EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            logger.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            chatMessage.setSender(username);

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
	
	/*
	 * Já estamos transmitindo o evento de um usuário conectar no método addUser() definido dentro dentro do ChatController. 
	 * Então, não precisamos fazer nada no evento SessionConnected.
	 *
	 * No evento SessionDisconnect, escrevemos código para extrair o nome do usuário da sessão do websocket e transmitir o evento de um usuário desconectar para todos os clients conectados.
	 */
}
