package com.lawfirm.model.cases.enums.reminder;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 提醒类型枚举
 */
@Getter
public enum ReminderTypeEnum implements BaseEnum<Integer> {

    /**
     * 开庭提醒
     */
    COURT_HEARING(1, "开庭提醒"),

    /**
     * 期限提醒
     */
    DEADLINE(2, "期限提醒"),

    /**
     * 任务提醒
     */
    TASK(3, "任务提醒"),

    /**
     * 会议提醒
     */
    MEETING(4, "会议提醒"),

    /**
     * 文件提交提醒
     */
    DOCUMENT_SUBMISSION(5, "文件提交提醒"),

    /**
     * 费用收取提醒
     */
    FEE_COLLECTION(6, "费用收取提醒"),

    /**
     * 证据交换提醒
     */
    EVIDENCE_EXCHANGE(7, "证据交换提醒"),

    /**
     * 客户沟通提醒
     */
    CLIENT_COMMUNICATION(8, "客户沟通提醒"),

    /**
     * 进度汇报提醒
     */
    PROGRESS_REPORT(9, "进度汇报提醒"),

    /**
     * 文件审核提醒
     */
    DOCUMENT_REVIEW(10, "文件审核提醒"),

    /**
     * 签署提醒
     */
    SIGNATURE(11, "签署提醒"),

    /**
     * 归档提醒
     */
    ARCHIVING(12, "归档提醒"),

    /**
     * 其他提醒
     */
    OTHER(99, "其他提醒");

    private final Integer value;
    private final String description;

    ReminderTypeEnum(Integer value, String description) {
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
    public static ReminderTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (ReminderTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否是重要提醒
     */
    public boolean isImportant() {
        return this == COURT_HEARING || this == DEADLINE || 
               this == DOCUMENT_SUBMISSION || this == EVIDENCE_EXCHANGE;
    }

    /**
     * 是否需要提前提醒
     */
    public boolean needAdvanceNotice() {
        return this == COURT_HEARING || this == DEADLINE || 
               this == DOCUMENT_SUBMISSION || this == EVIDENCE_EXCHANGE || 
               this == MEETING;
    }

    /**
     * 是否需要确认
     */
    public boolean needConfirmation() {
        return this == COURT_HEARING || this == MEETING || 
               this == FEE_COLLECTION || this == SIGNATURE;
    }

    /**
     * 是否需要多次提醒
     */
    public boolean needMultipleReminders() {
        return this == COURT_HEARING || this == DEADLINE || 
               this == DOCUMENT_SUBMISSION;
    }

    /**
     * 获取建议提前提醒时间（小时）
     */
    public int getSuggestedAdvanceHours() {
        switch (this) {
            case COURT_HEARING:
                return 48;
            case DEADLINE:
            case DOCUMENT_SUBMISSION:
                return 24;
            case MEETING:
            case EVIDENCE_EXCHANGE:
                return 12;
            case CLIENT_COMMUNICATION:
            case PROGRESS_REPORT:
                return 4;
            default:
                return 2;
        }
    }

    /**
     * 获取建议提醒次数
     */
    public int getSuggestedReminderCount() {
        switch (this) {
            case COURT_HEARING:
                return 3;
            case DEADLINE:
            case DOCUMENT_SUBMISSION:
                return 2;
            default:
                return 1;
        }
    }

    /**
     * 是否需要回执
     */
    public boolean needReceipt() {
        return this == DOCUMENT_SUBMISSION || this == FEE_COLLECTION || 
               this == SIGNATURE;
    }

    /**
     * 是否需要上报
     */
    public boolean needEscalation() {
        return this == COURT_HEARING || this == DEADLINE || 
               this == DOCUMENT_SUBMISSION;
    }
} 