package com.lawfirm.model.cases.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件时间线事件枚举
 */
@Getter
public enum CaseTimelineEventEnum implements BaseEnum<String> {
    
    // 案件流程事件
    CASE_CREATED("案件创建", EventGroup.PROCESS, true),
    CASE_ACCEPTED("案件受理", EventGroup.PROCESS, true),
    CASE_ASSIGNED("案件分配", EventGroup.PROCESS, true),
    CASE_TRANSFERRED("案件转办", EventGroup.PROCESS, true),
    CASE_CLOSED("案件结案", EventGroup.PROCESS, true),
    
    // 文书事件
    DOC_SUBMITTED("文书提交", EventGroup.DOCUMENT, false),
    DOC_RECEIVED("文书接收", EventGroup.DOCUMENT, true),
    DOC_APPROVED("文书批准", EventGroup.DOCUMENT, false),
    DOC_REJECTED("文书驳回", EventGroup.DOCUMENT, true),
    DOC_MODIFIED("文书修改", EventGroup.DOCUMENT, false),
    
    // 证据事件
    EVIDENCE_COLLECTED("证据收集", EventGroup.EVIDENCE, false),
    EVIDENCE_SUBMITTED("证据提交", EventGroup.EVIDENCE, true),
    EVIDENCE_EXCHANGED("证据交换", EventGroup.EVIDENCE, true),
    EVIDENCE_REVIEWED("证据审查", EventGroup.EVIDENCE, false),
    EVIDENCE_PRESERVED("证据保全", EventGroup.EVIDENCE, true),
    
    // 会见事件
    CLIENT_MEETING("客户会见", EventGroup.MEETING, false),
    COURT_MEETING("法院会见", EventGroup.MEETING, true),
    OPPONENT_MEETING("对方会见", EventGroup.MEETING, false),
    WITNESS_MEETING("证人会见", EventGroup.MEETING, false),
    EXPERT_MEETING("专家会见", EventGroup.MEETING, false),
    
    // 期限事件
    DEADLINE_SET("期限设定", EventGroup.DEADLINE, true),
    DEADLINE_UPDATED("期限更新", EventGroup.DEADLINE, false),
    DEADLINE_APPROACHING("期限临近", EventGroup.DEADLINE, true),
    DEADLINE_EXPIRED("期限届满", EventGroup.DEADLINE, true),
    DEADLINE_EXTENDED("期限延长", EventGroup.DEADLINE, true),
    
    // 费用事件
    FEE_ESTIMATED("费用预估", EventGroup.FINANCE, false),
    FEE_CONFIRMED("费用确认", EventGroup.FINANCE, true),
    FEE_RECEIVED("费用收取", EventGroup.FINANCE, true),
    FEE_REFUNDED("费用退还", EventGroup.FINANCE, true),
    FEE_ADJUSTED("费用调整", EventGroup.FINANCE, true),
    
    // 团队事件
    TEAM_ASSIGNED("团队指派", EventGroup.TEAM, true),
    TEAM_CHANGED("团队变更", EventGroup.TEAM, true),
    MEMBER_ADDED("成员加入", EventGroup.TEAM, false),
    MEMBER_REMOVED("成员移除", EventGroup.TEAM, false),
    ROLE_CHANGED("角色变更", EventGroup.TEAM, true),
    
    // 其他事件
    NOTE_ADDED("备注添加", EventGroup.OTHER, false),
    STATUS_CHANGED("状态变更", EventGroup.OTHER, true),
    RISK_IDENTIFIED("风险识别", EventGroup.OTHER, true),
    IMPORTANT_UPDATE("重要更新", EventGroup.OTHER, true);

    private final String description;
    private final EventGroup eventGroup;
    private final boolean needsRecord;  // 是否需要记录

    CaseTimelineEventEnum(String description, EventGroup eventGroup, boolean needsRecord) {
        this.description = description;
        this.eventGroup = eventGroup;
        this.needsRecord = needsRecord;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * 获取事件分组
     * @return 事件分组
     */
    public EventGroup getEventGroup() {
        return this.eventGroup;
    }

    /**
     * 判断是否需要记录
     * @return 是否需要记录
     */
    public boolean needsRecord() {
        return this.needsRecord;
    }

    /**
     * 判断是否为流程事件
     * @return 是否流程事件
     */
    public boolean isProcessEvent() {
        return this.eventGroup == EventGroup.PROCESS;
    }

    /**
     * 判断是否为文书事件
     * @return 是否文书事件
     */
    public boolean isDocumentEvent() {
        return this.eventGroup == EventGroup.DOCUMENT;
    }

    /**
     * 判断是否为证据事件
     * @return 是否证据事件
     */
    public boolean isEvidenceEvent() {
        return this.eventGroup == EventGroup.EVIDENCE;
    }

    /**
     * 判断是否为会见事件
     * @return 是否会见事件
     */
    public boolean isMeetingEvent() {
        return this.eventGroup == EventGroup.MEETING;
    }

    /**
     * 判断是否为期限事件
     * @return 是否期限事件
     */
    public boolean isDeadlineEvent() {
        return this.eventGroup == EventGroup.DEADLINE;
    }

    /**
     * 判断是否为费用事件
     * @return 是否费用事件
     */
    public boolean isFinanceEvent() {
        return this.eventGroup == EventGroup.FINANCE;
    }

    /**
     * 判断是否为团队事件
     * @return 是否团队事件
     */
    public boolean isTeamEvent() {
        return this.eventGroup == EventGroup.TEAM;
    }

    /**
     * 获取事件显示文本
     * @return 完整的事件显示文本
     */
    public String getEventDisplay() {
        if (this.needsRecord) {
            return this.description + "[!]";
        }
        return this.description;
    }

    /**
     * 事件分组
     */
    public enum EventGroup {
        PROCESS("流程事件"),
        DOCUMENT("文书事件"),
        EVIDENCE("证据事件"),
        MEETING("会见事件"),
        DEADLINE("期限事件"),
        FINANCE("费用事件"),
        TEAM("团队事件"),
        OTHER("其他事件");

        private final String description;

        EventGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 