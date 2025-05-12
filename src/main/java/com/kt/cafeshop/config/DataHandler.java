package com.kt.cafeshop.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DataHandler extends TextWebSocketHandler {
    private final Logger LOG = LoggerFactory.getLogger(DataHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Map<WebSocketSession, String> userSessions = new ConcurrentHashMap<>();

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        LOG.info("Received message: " + message.getPayload());
    }

    public void sendToUser(String username, Object messageObject) {
        try {
            String messageJson = objectMapper.writeValueAsString(messageObject);
            WebSocketSession session = sessions.get(username);
            if (session != null) {
                try {
                    session.sendMessage(new TextMessage(messageJson));
                } catch (Exception e) {
                    LOG.error("Failed to send message to user: " + username, e);
                }
            } else {
                LOG.warn("No session found for user: " + username);
            }
        } catch (JsonProcessingException e) {
            LOG.error("Failed to convert message object to JSON", e);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            sessions.put(userId, session);
            userSessions.put(session, userId);
        }
        LOG.info("Connection established: " + session.getId() + " for user: " + userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String userId = userSessions.remove(session);
        if (userId != null) {
            sessions.remove(userId);
        }
        LOG.info("Connection closed: " + session.getId() + " for user: " + userId);
    }

    private String getUserIdFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        LOG.error(query);
        return query != null ? query.split("userid=")[1] : null;
    }
}
