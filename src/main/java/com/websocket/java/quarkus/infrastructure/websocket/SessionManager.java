package com.websocket.java.quarkus.infrastructure.websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class SessionManager {

    private final ConcurrentHashMap<String, Set<Session>> rooms = new ConcurrentHashMap<>();

    public void addSession(String room, Session session) {
        rooms.computeIfAbsent(room, r -> ConcurrentHashMap.newKeySet()).add(session);
    }

    public void removeSession(String room, Session session) {
        rooms.getOrDefault(room, Set.of()).remove(session);
    }

    public void broadcast(String room, String message) {
        rooms.getOrDefault(room, Set.of())
                .forEach(s -> s.getAsyncRemote().sendText(message));
    }
}