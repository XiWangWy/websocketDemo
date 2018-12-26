package com.example.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * Created by wangxi on 2018/12/17.
 */
@Slf4j
public class MyWebSocketHandler extends TextWebSocketHandler {

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
            log.info("连接关闭"+ webSocketSession.toString());
            super.afterConnectionClosed(webSocketSession,closeStatus);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
            log.info("建立连接" + webSocketSession.toString());
            super.afterConnectionEstablished(webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("处理消息：======》" + ((TextMessage) webSocketMessage).getPayload());
       super.handleMessage(webSocketSession,webSocketMessage);
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
            if (webSocketSession.isOpen()){
                webSocketSession.close();
            }
            log.error("传输出现异常，关闭websocket连接...");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
