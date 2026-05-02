package com.websocket.java.quarkus.interfaces.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.java.quarkus.application.dto.ChatMessageDTO;
import com.websocket.java.quarkus.application.service.ChatService;
import com.websocket.java.quarkus.infrastructure.websocket.SessionManager;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.util.concurrent.CompletableFuture;

@ServerEndpoint("/chat/{room}/{user}")
public class ChatSocket {

    @Inject
    SessionManager sessionManager;

    @Inject
    ChatService service;

    @Inject
    ObjectMapper mapper;

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) {
        sessionManager.addSession(room, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("room") String room) {
        sessionManager.removeSession(room, session);
    }

    @OnMessage
    public void onMessage(String messageJson,
                          @PathParam("room") String room,
                          @PathParam("user") String user) {

        CompletableFuture.runAsync(() -> {
            try {
                ChatMessageDTO dto =
                        mapper.readValue(messageJson, ChatMessageDTO.class);

                // garante consistência
                dto.roomId = room;
                dto.sender = user;

                // 🔥 serializa corretamente
                String json = mapper.writeValueAsString(dto);

                // 🔥 envia JSON válido
                sessionManager.broadcast(room, json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}