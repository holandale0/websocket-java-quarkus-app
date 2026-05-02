package com.websocket.java.quarkus.infrastructure.redis;

import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.pubsub.PubSubCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class RedisPublisher {

    private final Logger logger = LoggerFactory.getLogger(RedisPublisher.class);

    private final PubSubCommands<String> pub;

    @Inject
    public RedisPublisher(RedisDataSource ds) {
        this.pub = ds.pubsub(String.class);
    }

    public void publish(String room, String message) {
        logger.info("RedisPublisher - Room: {}", room);
        logger.info("RedisPublisher - Message: {}", message);
        pub.publish(room, message);
    }
}