package com.websocket.java.quarkus.infrastructure.redis;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.websocket.java.quarkus.application.dto.ChatMessageDTO;
import com.websocket.java.quarkus.infrastructure.websocket.SessionManager;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RedisSubscriber {

    private final Logger logger = LoggerFactory.getLogger(RedisSubscriber.class);

    private final PubSubCommands<String> pub;

    @Inject
    ObjectMapper mapper;

    @Inject
    SessionManager sessionManager;

    @Inject
    public RedisSubscriber(RedisDataSource ds) {
        this.pub = ds.pubsub(String.class);
    }

    @PostConstruct
    void init() {

        pub.subscribe("chat", message -> {
            try {
                logger.info("RedisSubscriber - Message: {}", message);
                ChatMessageDTO dto =
                        mapper.readValue(message, ChatMessageDTO.class);

                // 🔥 envia JSON (não string formatada)
                String json = mapper.writeValueAsString(dto);

                logger.info("RedisSubscriber - json - Message: {}", json);

                sessionManager.broadcast(dto.roomId, json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}