package com.lawfirm.model.cases.enums.team;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 团队角色类型枚举
 */
@Getter
public enum TeamRoleTypeEnum implements BaseEnum<Integer> {

    /**
     * 主管角色
     */
    SUPERVISOR(1, "主管角色"),

    /**
     * 执行角色
     */
    EXECUTOR(2, "执行角色"),

    /**
     * 协助角色
     */
    ASSISTANT(3, "协助角色"),

    /**
     * 顾问角色
     */
    CONSULTANT(4, "顾问角色"),

    /**
     * 支持角色
     */
    SUPPORT(5, "支持角色");

    private final Integer value;
    private final String description;

    TeamRoleTypeEnum(Integer value, String description) {
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
    public static TeamRoleTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (TeamRoleTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否是管理层角色
     */
    public boolean isManagementRole() {
        return this == SUPERVISOR;
    }

    /**
     * 是否是核心角色
     */
    public boolean isCoreRole() {
        return this == SUPERVISOR || this == EXECUTOR;
    }

    /**
     * 是否可以分配任务
     */
    public boolean canAssignTasks() {
        return this == SUPERVISOR || this == EXECUTOR;
    }

    /**
     * 是否可以审核文件
     */
    public boolean canReviewDocuments() {
        return this == SUPERVISOR || this == CONSULTANT;
    }

    /**
     * 是否需要参加例会
     */
    public boolean needRegularMeeting() {
        return this == SUPERVISOR || this == EXECUTOR || this == ASSISTANT;
    }

    /**
     * 是否可以访问敏感信息
     */
    public boolean canAccessSensitiveInfo() {
        return this == SUPERVISOR || this == EXECUTOR;
    }

    /**
     * 是否需要提交工作报告
     */
    public boolean needWorkReport() {
        return this == EXECUTOR || this == ASSISTANT;
    }

    /**
     * 获取建议汇报周期（天）
     */
    public int getSuggestedReportCycle() {
        switch (this) {
            case EXECUTOR:
                return 7;
            case ASSISTANT:
                return 14;
            case CONSULTANT:
                return 30;
            default:
                return 0;
        }
    }

    /**
     * 获取建议会议频率（天）
     */
    public int getSuggestedMeetingFrequency() {
        switch (this) {
            case SUPERVISOR:
            case EXECUTOR:
                return 7;
            case ASSISTANT:
                return 14;
            case CONSULTANT:
                return 30;
            default:
                return 0;
        }
    }

    /**
     * 获取对应的团队成员角色
     */
    public TeamMemberRoleEnum[] getCorrespondingMemberRoles() {
        switch (this) {
            case SUPERVISOR:
                return new TeamMemberRoleEnum[]{
                    TeamMemberRoleEnum.SUPERVISING_PARTNER,
                    TeamMemberRoleEnum.LEAD_LAWYER
                };
            case EXECUTOR:
                return new TeamMemberRoleEnum[]{
                    TeamMemberRoleEnum.ASSOCIATE_LAWYER
                };
            case ASSISTANT:
                return new TeamMemberRoleEnum[]{
                    TeamMemberRoleEnum.LEGAL_ASSISTANT,
                    TeamMemberRoleEnum.DOCUMENT_ASSISTANT,
                    TeamMemberRoleEnum.TRAINEE_LAWYER
                };
            case CONSULTANT:
                return new TeamMemberRoleEnum[]{
                    TeamMemberRoleEnum.EXPERT_CONSULTANT
                };
            case SUPPORT:
                return new TeamMemberRoleEnum[]{
                    TeamMemberRoleEnum.ADMIN_ASSISTANT,
                    TeamMemberRoleEnum.TRANSLATOR,
                    TeamMemberRoleEnum.INVESTIGATOR
                };
            default:
                return new TeamMemberRoleEnum[]{};
        }
    }
} 