package com.lawfirm.core.message.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket配置类
 * 支持实时消息推送
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
@ConditionalOnProperty(prefix = "message", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用简单消息代理，并设置消息代理路径
        config.enableSimpleBroker("/topic", "/queue");
        // 设置应用程序目标前缀
        config.setApplicationDestinationPrefixes("/app");
        // 设置用户目标前缀
        config.setUserDestinationPrefix("/user");
        
        log.info("WebSocket消息代理配置完成");
    }

    /**
     * 注册STOMP端点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册WebSocket端点
        registry.addEndpoint("/ws-message")
                .setAllowedOriginPatterns("*")  // 允许跨域
                .withSockJS();  // 启用SockJS支持
        
        // 注册简单的WebSocket端点（不使用SockJS）
        registry.addEndpoint("/ws-message-native")
                .setAllowedOriginPatterns("*");
                
        log.info("WebSocket端点注册完成: /ws-message, /ws-message-native");
    }
} 