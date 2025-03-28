package com.lawfirm.core.message.config;

import lombok.Data;

/**
 * 消息服务配置属性
 */
@Data
public class MessageProperties {

    /**
     * 是否启用消息服务
     */
    private boolean enabled = true;
    
    /**
     * 发送者配置
     */
    private Sender sender = new Sender();
    
    /**
     * 接收者配置
     */
    private Receiver receiver = new Receiver();
    
    /**
     * 存储配置
     */
    private Storage storage = new Storage();
    
    /**
     * 安全配置
     */
    private Security security = new Security();
    
    /**
     * RocketMQ配置
     */
    private RocketMQ rocketmq = new RocketMQ();
    
    /**
     * 发送者配置
     */
    @Data
    public static class Sender {
        /**
         * 重试次数
         */
        private int retryTimes = 3;
        
        /**
         * 重试间隔（毫秒）
         */
        private int retryInterval = 1000;
        
        /**
         * 异步线程池大小
         */
        private int asyncPoolSize = 5;
    }
    
    /**
     * 接收者配置
     */
    @Data
    public static class Receiver {
        /**
         * 线程池大小
         */
        private int poolSize = 10;
        
        /**
         * 最大并发数
         */
        private int maxConcurrent = 100;
    }
    
    /**
     * 存储配置
     */
    @Data
    public static class Storage {
        /**
         * 存储类型（redis/database）
         */
        private String type = "redis";
        
        /**
         * 过期时间
         */
        private String ttl = "7d";
    }
    
    /**
     * 安全配置
     */
    @Data
    public static class Security {
        /**
         * 加密配置
         */
        private Encrypt encrypt = new Encrypt();
        
        /**
         * 审计配置
         */
        private Audit audit = new Audit();
        
        /**
         * 加密配置
         */
        @Data
        public static class Encrypt {
            /**
             * 是否启用加密
             */
            private boolean enabled = true;
            
            /**
             * 加密算法
             */
            private String algorithm = "AES";
            
            /**
             * 加密密钥
             */
            private String key = "default-key";
        }
        
        /**
         * 审计配置
         */
        @Data
        public static class Audit {
            /**
             * 是否启用审计
             */
            private boolean enabled = true;
            
            /**
             * 审计日志路径
             */
            private String logPath = "/var/log/lawfirm/message-audit.log";
        }
    }
    
    /**
     * RocketMQ配置
     */
    @Data
    public static class RocketMQ {
        /**
         * 主题
         */
        private String topic = "law-firm-message";
        
        /**
         * 消费者组
         */
        private String consumerGroup = "law-firm-message-group";
        
        /**
         * 名称服务器
         */
        private String nameServer = "localhost:9876";
        
        /**
         * 生产者组
         */
        private String producerGroup = "law-firm-producer-group";
    }
} 