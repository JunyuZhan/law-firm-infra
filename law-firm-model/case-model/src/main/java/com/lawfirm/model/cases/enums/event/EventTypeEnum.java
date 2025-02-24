package com.lawfirm.model.cases.enums.event;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 事件类型枚举
 */
@Getter
public enum EventTypeEnum implements BaseEnum<Integer> {

    /**
     * 开庭
     */
    COURT_HEARING(1, "开庭"),

    /**
     * 证据交换
     */
    EVIDENCE_EXCHANGE(2, "证据交换"),

    /**
     * 调解
     */
    MEDIATION(3, "调解"),

    /**
     * 客户会见
     */
    CLIENT_MEETING(4, "客户会见"),

    /**
     * 团队会议
     */
    TEAM_MEETING(5, "团队会议"),

    /**
     * 文书提交
     */
    DOCUMENT_SUBMISSION(6, "文书提交"),

    /**
     * 证据收集
     */
    EVIDENCE_COLLECTION(7, "证据收集"),

    /**
     * 现场调查
     */
    SITE_INVESTIGATION(8, "现场调查"),

    /**
     * 谈判
     */
    NEGOTIATION(9, "谈判"),

    /**
     * 费用收取
     */
    FEE_COLLECTION(10, "费用收取"),

    /**
     * 案件汇报
     */
    CASE_REPORT(11, "案件汇报"),

    /**
     * 法律咨询
     */
    LEGAL_CONSULTATION(12, "法律咨询"),

    /**
     * 文件送达
     */
    DOCUMENT_SERVICE(13, "文件送达"),

    /**
     * 判决送达
     */
    JUDGMENT_SERVICE(14, "判决送达"),

    /**
     * 执行申请
     */
    ENFORCEMENT_APPLICATION(15, "执行申请"),

    /**
     * 其他事项
     */
    OTHER(99, "其他事项");

    private final Integer value;
    private final String description;

    EventTypeEnum(Integer value, String description) {
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
    public static EventTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (EventTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否是重要事件
     */
    public boolean isImportant() {
        return this == COURT_HEARING || this == EVIDENCE_EXCHANGE || 
               this == MEDIATION || this == JUDGMENT_SERVICE;
    }

    /**
     * 是否需要提前提醒
     */
    public boolean needAdvanceReminder() {
        return this == COURT_HEARING || this == EVIDENCE_EXCHANGE || 
               this == CLIENT_MEETING || this == DOCUMENT_SUBMISSION;
    }

    /**
     * 是否需要会议记录
     */
    public boolean needMeetingMinutes() {
        return this == COURT_HEARING || this == CLIENT_MEETING || 
               this == TEAM_MEETING || this == MEDIATION || 
               this == NEGOTIATION;
    }

    /**
     * 是否需要签到
     */
    public boolean needSignIn() {
        return this == COURT_HEARING || this == CLIENT_MEETING || 
               this == TEAM_MEETING;
    }

    /**
     * 是否需要照片记录
     */
    public boolean needPhotoRecord() {
        return this == SITE_INVESTIGATION || this == EVIDENCE_COLLECTION;
    }

    /**
     * 是否需要客户确认
     */
    public boolean needClientConfirmation() {
        return this == CLIENT_MEETING || this == FEE_COLLECTION || 
               this == LEGAL_CONSULTATION;
    }

    /**
     * 是否需要回执
     */
    public boolean needReceipt() {
        return this == DOCUMENT_SERVICE || this == JUDGMENT_SERVICE;
    }

    /**
     * 获取建议提前提醒时间（小时）
     */
    public int getSuggestedReminderHours() {
        switch (this) {
            case COURT_HEARING:
                return 24;
            case EVIDENCE_EXCHANGE:
            case CLIENT_MEETING:
            case TEAM_MEETING:
                return 12;
            case DOCUMENT_SUBMISSION:
                return 48;
            default:
                return 2;
        }
    }
} 