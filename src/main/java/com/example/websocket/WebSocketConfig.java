package com.example.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.config.annotation.*;

/**
 * Created by wangxi on 2018/12/14.
 */
@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
//混合使用原始的websocket和stomp
public class WebSocketConfig implements WebSocketConfigurer,WebSocketMessageBrokerConfigurer {
    @Autowired
    private  MyHandShakeInterceptor myHandShakeInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(myWebSocketHandler(),"/webSocket")
                .setAllowedOrigins("*")
                .addInterceptors(myHandShakeInterceptor)
                .withSockJS().setHeartbeatTime(2000);
    }

    @Bean
    public MyWebSocketHandler myWebSocketHandler(){
        return new MyWebSocketHandler();
    }



    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        ThreadPoolTaskScheduler te = new ThreadPoolTaskScheduler();
        te.setPoolSize(1);
        te.setThreadNamePrefix("wss-heartbeat-thread-");
        te.initialize();

//        config.enableStompBrokerRelay()
        config.enableSimpleBroker("/topic","/queue","/user").setHeartbeatValue(new long[]{5000,5000}).setTaskScheduler(te);
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/webSocketStomp")
                .addInterceptors(myHandShakeInterceptor)
                .setAllowedOrigins("*")
                .withSockJS();
    }


}
