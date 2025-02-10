package com.lawfirm.common.message.constant;

/**
 * 消息常量
 */
public class MessageConstants {
    
    /**
     * Redis Key 前缀
     */
    public static class RedisKeyPrefix {
        public static final String MESSAGE_DETAIL = "message:detail:";
        public static final String USER_MESSAGE = "message:user:";
        public static final String USER_UNREAD = "message:unread:";
        public static final String MESSAGE_TEMPLATE = "message:template:";
        public static final String USER_SETTING = "message:setting:";
        public static final String USER_SUBSCRIPTION = "message:subscription:";
        public static final String MESSAGE_IDEMPOTENT = "message:idempotent:";
    }
    
    /**
     * 消息队列
     */
    public static class Queue {
        public static final String MESSAGE_DIRECT = "message.direct";
        public static final String MESSAGE_DELAY = "message.delay";
        public static final String MESSAGE_DLX = "message.dlx";
        public static final String MESSAGE_DLQ = "message.dlq";
    }
    
    /**
     * 缓存过期时间
     */
    public static class ExpireTime {
        public static final int MESSAGE_EXPIRE_DAYS = 7;
        public static final int TEMPLATE_EXPIRE_DAYS = 30;
        public static final int SETTING_EXPIRE_DAYS = 7;
        public static final int IDEMPOTENT_EXPIRE_HOURS = 24;
    }
    
    /**
     * 消息状态
     */
    public static class Status {
        public static final String PENDING = "PENDING";
        public static final String SENT = "SENT";
        public static final String FAILED = "FAILED";
        public static final String RECALLED = "RECALLED";
    }
    
    /**
     * 消息优先级
     */
    public static class Priority {
        public static final int LOW = 1;
        public static final int NORMAL = 5;
        public static final int HIGH = 8;
        public static final int URGENT = 9;
    }
} 