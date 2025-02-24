package com.lawfirm.model.cases.constants;

import com.lawfirm.model.base.constants.BaseConstants;
import java.time.temporal.ChronoUnit;

/**
 * 提醒规则常量
 */
public interface ReminderConstants extends BaseConstants {
    
    /**
     * 提醒时间限制
     */
    interface Time {
        int MAX_ADVANCE_DAYS = 90; // 最大提前天数
        int MIN_ADVANCE_DAYS = 1; // 最小提前天数
        int DEFAULT_ADVANCE_DAYS = 7; // 默认提前天数
    }
    
    /**
     * 提醒间隔限制
     */
    interface Interval {
        int MIN_MINUTES = 5; // 最小提醒间隔（分钟）
        int DEFAULT_HOURS = 24; // 默认提醒间隔（小时）
        int MAX_DAYS = 30; // 最大提醒间隔（天）
    }
    
    /**
     * 重复提醒限制
     */
    interface Repeat {
        int MAX_TIMES = 3; // 最大重复次数
        int DEFAULT_TIMES = 1; // 默认重复次数
    }
    
    /**
     * 提醒优先级
     */
    interface Priority {
        int HIGH = 1;
        int MEDIUM = 2;
        int LOW = 3;
    }
    
    /**
     * 提醒消息限制
     */
    interface Message {
        int TITLE_MAX_LENGTH = 100;
        int CONTENT_MAX_LENGTH = 500;
        int REMARK_MAX_LENGTH = 200;
    }
    
    /**
     * 提醒类型限制
     */
    interface Type {
        int MAX_TYPES_PER_CASE = 10;
        int TYPE_NAME_MAX_LENGTH = 50;
    }
    
    /**
     * 提醒接收人限制
     */
    interface Recipient {
        int MAX_PER_REMINDER = 20;
        int MIN_PER_REMINDER = 1;
    }
    
    /**
     * 提醒状态超时时间
     */
    interface Timeout {
        long HOURS = 48; // 提醒状态超时时间（小时）
        long EXPIRE_DAYS = 7; // 提醒过期天数
    }
    
    /**
     * 批量操作限制，继承自BaseConstants.Default
     */
    interface Batch extends Default {
        int MAX_CREATE = 50;
        int MAX_UPDATE = 100;
        int MAX_DELETE = 100;
    }
    
    /**
     * 提醒时间单位
     */
    interface TimeUnit {
        ChronoUnit DEFAULT = ChronoUnit.DAYS;
    }
    
    /**
     * 提醒模板限制
     */
    interface Template {
        int MAX_COUNT = 50;
        int NAME_MAX_LENGTH = 50;
        int CONTENT_MAX_LENGTH = 1000;
    }
} 