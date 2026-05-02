package com.websocket.java.quarkus.application.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.websocket.java.quarkus.application.dto.ChatMessageDTO;
import com.websocket.java.quarkus.domain.model.Message;
import com.websocket.java.quarkus.domain.repository.MessageRepository;
import com.websocket.java.quarkus.infrastructure.redis.RedisPublisher;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ChatService {

    @Inject
    RedisPublisher redis;

    @Inject
    MessageRepository repository;

    @Inject
    ObjectMapper mapper;

    public void sendMessage(ChatMessageDTO dto) {

        try {
            // 🔥 JSON pronto
            String payload = mapper.writeValueAsString(dto);

            // 🔥 publica no Redis
            redis.publish("chat", payload);

            // 🔥 persiste
            repository.save(
                    new Message(dto.roomId, dto.sender, dto.content)
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}