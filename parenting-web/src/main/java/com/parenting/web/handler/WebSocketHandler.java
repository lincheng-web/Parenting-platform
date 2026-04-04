package com.parenting.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket处理器
 */
@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(WebSocketHandler.class);

    private final Map<Long, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = (String) session.getAttributes().get("token");
        if (token != null) {
            Long userId = parseUserIdFromToken(token);
            if (userId != null) {
                sessionMap.put(userId, session);
                log.info("用户连接: {}", userId);
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.entrySet().removeIf(entry -> entry.getValue().equals(session));
        log.info("用户断开连接");
    }

    public void pushMessage(Long userId, String message) {
        WebSocketSession session = sessionMap.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                log.error("推送消息失败", e);
            }
        }
    }

    private Long parseUserIdFromToken(String token) {
        try {
            return Long.parseLong(token);
        } catch (Exception e) {
            return null;
        }
    }
}
