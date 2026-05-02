package com.websocket.java.quarkus.application.dto;

import java.time.LocalDateTime;

public class ChatMessageDTO {

    public String roomId;
    public String sender;
    public String content;
    public LocalDateTime timestamp;

    public ChatMessageDTO() {}

    public ChatMessageDTO(String roomId, String sender, String content) {
        this.roomId = roomId;
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();
    }
}