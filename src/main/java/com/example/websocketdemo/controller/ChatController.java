package com.example.websocketdemo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.example.websocketdemo.model.ChatMessage;

@Controller
public class ChatController {
	
	/*
	 * Relembrando a configuração do websocket, todas as mensagens enviadas de clients com destino começando com /app vão ser enviadas para esses métodos de message-handling com a anotação @MessageMapping.
	 *
	 * Por exemplo, uma mensagem com destino /app/chat.sendMessage será direcionada para o método sendMessage(), 
	 * e uma mensagem com destino /app/chat.addUser será direcionada para o método addUser().
	 */

	@MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        // Coloca o username na sessão do websocket
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
    
}
