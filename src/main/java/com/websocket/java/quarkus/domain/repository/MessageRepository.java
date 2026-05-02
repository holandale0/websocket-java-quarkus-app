package com.websocket.java.quarkus.domain.repository;

import com.websocket.java.quarkus.domain.model.Message;

public interface MessageRepository {
    void save(Message message);
}