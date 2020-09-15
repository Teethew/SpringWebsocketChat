package com.example.websocketdemo.model;

public class ChatMessage { //O modelo ChatMessage é o payload da mensagem que será enviado entre os clients e o servidor.

	private MessageType type;
    private String content;
    private String sender;
    
	public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }

	//getters e setters
	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	
}
