package com.lawfirm.core.message.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 消息配置类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "message")
public class MessageConfig {
    
    /**
     * 阿里云短信配置
     */
    private AliyunSmsConfig sms = new AliyunSmsConfig();
    
    /**
     * 微信配置
     */
    private WechatConfig wechat = new WechatConfig();
    
    /**
     * Redis配置
     */
    private RedisConfig redis = new RedisConfig();
    
    @Bean
    public Client smsClient() throws Exception {
        Config config = new Config()
                .setAccessKeyId(sms.getAccessKeyId())
                .setAccessKeySecret(sms.getAccessKeySecret())
                .setEndpoint(sms.getEndpoint());
        return new Client(config);
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Data
    public static class AliyunSmsConfig {
        private String accessKeyId;
        private String accessKeySecret;
        private String endpoint = "dysmsapi.aliyuncs.com";
        private String signName;
    }
    
    @Data
    public static class WechatConfig {
        private String appId;
        private String appSecret;
        private String token;
        private String aesKey;
    }
    
    @Data
    public static class RedisConfig {
        private String messageKeyPrefix = "message:user:";
        private String subscriptionKeyPrefix = "message:subscription:";
        private Integer messageExpireDays = 7;
    }
} 