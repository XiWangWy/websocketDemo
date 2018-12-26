package com.example.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * Created by wangxi on 2018/12/17.
 */
@Component
@Slf4j
public class MyHandShakeInterceptor implements HandshakeInterceptor {
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        ServletServerHttpRequest req = (ServletServerHttpRequest) request;
       log.info("headers===>" + req.getHeaders().toString());
        //获取token认证
        String token = req.getServletRequest().getParameter("token");

        log.info(this.getClass().getCanonicalName() + "http协议转换websoket协议进行前, 握手前"+request.getURI() + "             token: " + token);
        // http协议转换websoket协议进行前，可以在这里通过session信息判断用户登录是否合法
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        //握手成功后,
        log.info(this.getClass().getCanonicalName() + "握手成功后...");
    }
}
