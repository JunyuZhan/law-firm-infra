package com.lawfirm.core.message.constant;

public class MessageConstants {
    // RabbitMQ 相关常量
    public static final String EXCHANGE_MESSAGE = "message.exchange";
    public static final String QUEUE_MESSAGE = "message.queue";
    public static final String ROUTING_KEY_MESSAGE = "message.routing.key";
    public static final String QUEUE_DELAYED_MESSAGE = "message.delayed.queue";
    public static final String ROUTING_KEY_DELAYED_MESSAGE = "message.delayed.routing.key";

    // Redis 相关常量
    public static final String REDIS_KEY_MESSAGE_PREFIX = "message:";
    public static final String REDIS_KEY_MESSAGE_TEMPLATE_PREFIX = "message:template:";
    public static final String REDIS_KEY_USER_SETTING_PREFIX = "message:user:setting:";

    // 消息状态
    public static final String MESSAGE_STATUS_UNREAD = "unread";
    public static final String MESSAGE_STATUS_READ = "read";
    public static final String MESSAGE_STATUS_DELETED = "deleted";
    public static final String MESSAGE_STATUS_PENDING = "pending";
    public static final String MESSAGE_STATUS_SENT = "sent";
    public static final String MESSAGE_STATUS_FAILED = "failed";

    // 错误码
    public static final String ERROR_CODE_TEMPLATE_NOT_FOUND = "TEMPLATE_NOT_FOUND";
    public static final String ERROR_CODE_USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String ERROR_CODE_SEND_FAILED = "SEND_FAILED";
    public static final String ERROR_CODE_INVALID_PARAMETER = "INVALID_PARAMETER";
    public static final String ERROR_CODE_RATE_LIMIT_EXCEEDED = "RATE_LIMIT_EXCEEDED";
    public static final String ERROR_CODE_SYSTEM_ERROR = "SYSTEM_ERROR";
    public static final String ERROR_CODE_TEMPLATE_EXISTS = "TEMPLATE_EXISTS";
    public static final String ERROR_CODE_MESSAGE_NOT_FOUND = "MESSAGE_NOT_FOUND";
    public static final String ERROR_CODE_UNAUTHORIZED = "UNAUTHORIZED";

    // 业务类型
    public static final String BUSINESS_TYPE_SYSTEM = "SYSTEM";
    public static final String BUSINESS_TYPE_USER = "USER";
    public static final String BUSINESS_TYPE_ORDER = "ORDER";
    public static final String BUSINESS_TYPE_PAYMENT = "PAYMENT";

    // 缓存过期时间（秒）
    public static final long CACHE_EXPIRE_TIME = 7 * 24 * 60 * 60L; // 7天
    public static final long TEMPLATE_CACHE_EXPIRE_TIME = 24 * 60 * 60L; // 1天
    public static final long USER_SETTING_CACHE_EXPIRE_TIME = 12 * 60 * 60L; // 12小时

    public static class RedisKeyPrefix {
        public static final String MESSAGE = "message:";
        public static final String USER_MESSAGE = "message:user:";
        public static final String USER_SETTING = "message:setting:";
        public static final String MESSAGE_TEMPLATE = "message:template:";
        public static final String MESSAGE_SUBSCRIPTION = "message:subscription:";
        public static final String MESSAGE_DETAIL = "message:detail:";
        public static final String USER_UNREAD = "message:unread:";
    }
    
    public static class ExpireTime {
        public static final int MESSAGE = 7 * 24 * 60 * 60;  // 7天
        public static final int USER_SETTING = 30 * 24 * 60 * 60;  // 30天
        public static final int TEMPLATE = 7 * 24 * 60 * 60;  // 7天
        public static final int SUBSCRIPTION = 24 * 60 * 60;  // 1天
        public static final int MESSAGE_EXPIRE_DAYS = 30;
    }

    private MessageConstants() {
        // 私有构造函数，防止实例化
    }
} 