package com.websocket.java.quarkus.infrastructure.persistence.repository;



import com.websocket.java.quarkus.domain.model.Message;
import com.websocket.java.quarkus.domain.repository.MessageRepository;
import com.websocket.java.quarkus.infrastructure.persistence.entity.MessageEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class MessageRepositoryImpl implements MessageRepository {

    @Override
    @Transactional
    public void save(Message message) {
        MessageEntity entity = new MessageEntity();
        entity.roomId = message.getRoomId();
        entity.sender = message.getSender();
        entity.content = message.getContent();
        entity.timestamp = message.getTimestamp();

        entity.persist();
    }
}