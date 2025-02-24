package com.lawfirm.model.cases.enums.event;

import com.lawfirm.model.base.enums.BaseEnum;
import com.lawfirm.model.cases.enums.base.CaseProgressEnum;
import lombok.Getter;

/**
 * 案件时间线事件枚举
 */
@Getter
public enum CaseTimelineEventEnum implements BaseEnum<Integer> {

    /**
     * 案件受理
     */
    CASE_ACCEPTED(1, "案件受理", CaseProgressEnum.ACCEPTED),

    /**
     * 签订委托合同
     */
    CONTRACT_SIGNED(2, "签订委托合同", CaseProgressEnum.PREPARING),

    /**
     * 收取律师费
     */
    FEE_COLLECTED(3, "收取律师费", CaseProgressEnum.PREPARING),

    /**
     * 组建办案团队
     */
    TEAM_FORMED(4, "组建办案团队", CaseProgressEnum.PREPARING),

    /**
     * 制定办案计划
     */
    PLAN_MADE(5, "制定办案计划", CaseProgressEnum.PREPARING),

    /**
     * 收集证据材料
     */
    EVIDENCE_COLLECTED(6, "收集证据材料", CaseProgressEnum.PREPARING),

    /**
     * 提交立案材料
     */
    FILING_SUBMITTED(7, "提交立案材料", CaseProgressEnum.PREPARING),

    /**
     * 法院立案
     */
    COURT_FILING(8, "法院立案", CaseProgressEnum.FILED),

    /**
     * 送达诉讼材料
     */
    DOCUMENTS_SERVED(9, "送达诉讼材料", CaseProgressEnum.IN_TRIAL),

    /**
     * 开庭审理
     */
    COURT_HEARING(10, "开庭审理", CaseProgressEnum.IN_TRIAL),

    /**
     * 法院调解
     */
    COURT_MEDIATION(11, "法院调解", CaseProgressEnum.IN_MEDIATION),

    /**
     * 达成调解协议
     */
    MEDIATION_REACHED(12, "达成调解协议", CaseProgressEnum.IN_MEDIATION),

    /**
     * 法院判决
     */
    COURT_JUDGMENT(13, "法院判决", CaseProgressEnum.JUDGED),

    /**
     * 提起上诉
     */
    APPEAL_FILED(14, "提起上诉", CaseProgressEnum.IN_APPEAL),

    /**
     * 申请执行
     */
    ENFORCEMENT_APPLIED(15, "申请执行", CaseProgressEnum.IN_ENFORCEMENT),

    /**
     * 执行完毕
     */
    ENFORCEMENT_COMPLETED(16, "执行完毕", CaseProgressEnum.IN_ENFORCEMENT),

    /**
     * 结案归档
     */
    CASE_CLOSED(17, "结案归档", CaseProgressEnum.CLOSED),

    /**
     * 案件重启
     */
    CASE_REOPENED(18, "案件重启", CaseProgressEnum.REOPENED),

    /**
     * 变更主办律师
     */
    LAWYER_CHANGED(19, "变更主办律师", null),

    /**
     * 变更案件信息
     */
    INFO_UPDATED(20, "变更案件信息", null);

    private final Integer value;
    private final String description;
    private final CaseProgressEnum progress;

    CaseTimelineEventEnum(Integer value, String description, CaseProgressEnum progress) {
        this.value = value;
        this.description = description;
        this.progress = progress;
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
     * 获取关联的案件进度
     */
    public CaseProgressEnum getProgress() {
        return progress;
    }

    /**
     * 根据值获取枚举
     */
    public static CaseTimelineEventEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (CaseTimelineEventEnum event : values()) {
            if (event.value.equals(value)) {
                return event;
            }
        }
        return null;
    }

    /**
     * 根据案件进度获取可能的事件列表
     */
    public static CaseTimelineEventEnum[] getEventsByProgress(CaseProgressEnum progress) {
        if (progress == null) {
            return new CaseTimelineEventEnum[]{};
        }
        return java.util.Arrays.stream(values())
            .filter(event -> event.progress == progress)
            .toArray(CaseTimelineEventEnum[]::new);
    }

    /**
     * 是否是关键节点
     */
    public boolean isKeyPoint() {
        return this == CASE_ACCEPTED || this == COURT_FILING || 
               this == COURT_HEARING || this == COURT_JUDGMENT || 
               this == CASE_CLOSED;
    }

    /**
     * 是否需要文档记录
     */
    public boolean needDocumentation() {
        return this != INFO_UPDATED;
    }

    /**
     * 是否需要客户确认
     */
    public boolean needClientConfirmation() {
        return this == CONTRACT_SIGNED || this == FEE_COLLECTED || 
               this == MEDIATION_REACHED;
    }

    /**
     * 是否需要合伙人审批
     */
    public boolean needPartnerApproval() {
        return this == CASE_ACCEPTED || this == LAWYER_CHANGED || 
               this == CASE_CLOSED || this == CASE_REOPENED;
    }

    /**
     * 是否需要团队成员知晓
     */
    public boolean needTeamNotification() {
        return this != INFO_UPDATED;
    }

    /**
     * 是否需要更新案件状态
     */
    public boolean needUpdateCaseStatus() {
        return this == CASE_ACCEPTED || this == CASE_CLOSED || 
               this == CASE_REOPENED;
    }

    /**
     * 是否需要更新案件进度
     */
    public boolean needUpdateCaseProgress() {
        return this.progress != null;
    }

    /**
     * 获取事件重要性级别（1-5，5最重要）
     */
    public int getImportanceLevel() {
        switch (this) {
            case CASE_ACCEPTED:
            case COURT_HEARING:
            case COURT_JUDGMENT:
            case CASE_CLOSED:
                return 5;
            case CONTRACT_SIGNED:
            case COURT_FILING:
            case ENFORCEMENT_APPLIED:
                return 4;
            case FEE_COLLECTED:
            case MEDIATION_REACHED:
            case ENFORCEMENT_COMPLETED:
                return 3;
            case TEAM_FORMED:
            case EVIDENCE_COLLECTED:
            case LAWYER_CHANGED:
                return 2;
            default:
                return 1;
        }
    }
} 