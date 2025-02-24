package com.lawfirm.core.message.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "message")
public class MessageProperties {
    private Email email = new Email();
    private Sms sms = new Sms();
    private Wechat wechat = new Wechat();
    private WebSocket webSocket = new WebSocket();
    private RabbitMQ rabbitMQ = new RabbitMQ();

    @Data
    public static class Email {
        private String host;
        private Integer port;
        private String username;
        private String password;
        private String from;
        private Boolean ssl = false;
    }

    @Data
    public static class Sms {
        private String accessKeyId;
        private String accessKeySecret;
        private String signName;
        private String templateCode;
        private String endpoint = "dysmsapi.aliyuncs.com";
    }

    @Data
    public static class Wechat {
        private String appId;
        private String appSecret;
        private String token;
        private String aesKey;
        private String templateId;
    }

    @Data
    public static class WebSocket {
        private Integer heartbeatInterval = 30;
        private Integer sessionTimeout = 1800;
    }

    @Data
    public static class RabbitMQ {
        private String exchangeName = "message.exchange";
        private String queueName = "message.queue";
        private String routingKey = "message.routing.key";
        private String dlxExchange = "message.dlx";
        private String dlqName = "message.dlq";
    }
} 