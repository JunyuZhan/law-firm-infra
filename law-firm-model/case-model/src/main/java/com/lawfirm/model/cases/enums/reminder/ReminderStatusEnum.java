package com.lawfirm.model.cases.enums.reminder;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 提醒状态枚举
 */
@Getter
public enum ReminderStatusEnum implements BaseEnum<Integer> {

    /**
     * 待发送
     */
    PENDING(1, "待发送"),

    /**
     * 已发送
     */
    SENT(2, "已发送"),

    /**
     * 已接收
     */
    RECEIVED(3, "已接收"),

    /**
     * 已确认
     */
    CONFIRMED(4, "已确认"),

    /**
     * 已完成
     */
    COMPLETED(5, "已完成"),

    /**
     * 已取消
     */
    CANCELLED(6, "已取消"),

    /**
     * 已过期
     */
    EXPIRED(7, "已过期"),

    /**
     * 已忽略
     */
    IGNORED(8, "已忽略"),

    /**
     * 需要重发
     */
    NEED_RESEND(9, "需要重发"),

    /**
     * 已上报
     */
    ESCALATED(10, "已上报");

    private final Integer value;
    private final String description;

    ReminderStatusEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    /**
     * 根据值获取枚举
     */
    public static ReminderStatusEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (ReminderStatusEnum status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * 是否是活跃状态
     */
    public boolean isActive() {
        return this == PENDING || this == SENT || this == RECEIVED || 
               this == NEED_RESEND;
    }

    /**
     * 是否是终态
     */
    public boolean isTerminal() {
        return this == COMPLETED || this == CANCELLED || this == EXPIRED || 
               this == IGNORED;
    }

    /**
     * 是否需要跟进
     */
    public boolean needFollowUp() {
        return this == SENT || this == NEED_RESEND;
    }

    /**
     * 是否可以重发
     */
    public boolean canResend() {
        return this == NEED_RESEND || this == EXPIRED;
    }

    /**
     * 是否需要确认
     */
    public boolean needConfirmation() {
        return this == SENT || this == RECEIVED;
    }

    /**
     * 是否需要上报
     */
    public boolean needEscalation() {
        return this == EXPIRED || this == IGNORED;
    }

    /**
     * 获取下一个可能的状态
     */
    public ReminderStatusEnum[] getNextPossibleStatus() {
        switch (this) {
            case PENDING:
                return new ReminderStatusEnum[]{SENT, CANCELLED};
            case SENT:
                return new ReminderStatusEnum[]{RECEIVED, NEED_RESEND, EXPIRED, CANCELLED};
            case RECEIVED:
                return new ReminderStatusEnum[]{CONFIRMED, IGNORED, CANCELLED};
            case CONFIRMED:
                return new ReminderStatusEnum[]{COMPLETED, CANCELLED};
            case NEED_RESEND:
                return new ReminderStatusEnum[]{SENT, CANCELLED};
            case EXPIRED:
                return new ReminderStatusEnum[]{NEED_RESEND, ESCALATED, CANCELLED};
            case IGNORED:
                return new ReminderStatusEnum[]{ESCALATED, CANCELLED};
            default:
                return new ReminderStatusEnum[]{};
        }
    }

    /**
     * 是否需要记录原因
     */
    public boolean needReason() {
        return this == CANCELLED || this == IGNORED || this == NEED_RESEND;
    }

    /**
     * 是否需要通知管理员
     */
    public boolean needAdminNotification() {
        return this == EXPIRED || this == IGNORED || this == ESCALATED;
    }
} 