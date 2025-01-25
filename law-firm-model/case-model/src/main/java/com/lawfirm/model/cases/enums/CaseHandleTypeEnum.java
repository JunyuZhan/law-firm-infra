package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件办理方式枚举
 */
@Getter
public enum CaseHandleTypeEnum implements BaseEnum<String> {
    
    SOLE_LAWYER("独立办理", HandleGroup.INDIVIDUAL, 1),
    TEAM_LEADER("团队负责", HandleGroup.TEAM, 3),
    TEAM_MEMBER("团队协办", HandleGroup.TEAM, 2),
    SUPERVISOR("督办指导", HandleGroup.SUPERVISION, 1),
    CONSULTANT("法律顾问", HandleGroup.ADVISORY, 1),
    COOPERATE_MAIN("主办协办", HandleGroup.COOPERATION, 2),
    COOPERATE_ASSIST("协助办理", HandleGroup.COOPERATION, 1),
    EXPERT_ADVISOR("专家顾问", HandleGroup.ADVISORY, 1),
    SPECIAL_ADVISOR("特邀顾问", HandleGroup.ADVISORY, 1);

    private final String description;
    private final HandleGroup handleGroup;
    private final Integer requiredLawyers;  // 所需律师人数

    CaseHandleTypeEnum(String description, HandleGroup handleGroup, Integer requiredLawyers) {
        this.description = description;
        this.handleGroup = handleGroup;
        this.requiredLawyers = requiredLawyers;
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
     * 获取办理方式分组
     * @return 办理方式分组
     */
    public HandleGroup getHandleGroup() {
        return this.handleGroup;
    }

    /**
     * 获取所需律师人数
     * @return 所需律师人数
     */
    public Integer getRequiredLawyers() {
        return this.requiredLawyers;
    }

    /**
     * 判断是否为团队办理
     * @return 是否团队办理
     */
    public boolean isTeamHandle() {
        return this.handleGroup == HandleGroup.TEAM;
    }

    /**
     * 判断是否为个人办理
     * @return 是否个人办理
     */
    public boolean isIndividualHandle() {
        return this.handleGroup == HandleGroup.INDIVIDUAL;
    }

    /**
     * 判断是否为协作办理
     * @return 是否协作办理
     */
    public boolean isCooperativeHandle() {
        return this.handleGroup == HandleGroup.COOPERATION;
    }

    /**
     * 判断是否为顾问方式
     * @return 是否顾问方式
     */
    public boolean isAdvisoryHandle() {
        return this.handleGroup == HandleGroup.ADVISORY;
    }

    /**
     * 判断是否为主要办理人
     * @return 是否主要办理人
     */
    public boolean isMainHandler() {
        return this == SOLE_LAWYER || this == TEAM_LEADER || this == COOPERATE_MAIN;
    }

    /**
     * 判断是否需要团队协作
     * @return 是否需要团队协作
     */
    public boolean needsTeamwork() {
        return this.requiredLawyers > 1;
    }

    /**
     * 获取办理方式说明
     * @return 完整的办理方式说明
     */
    public String getHandleDescription() {
        StringBuilder desc = new StringBuilder(this.description);
        if (this.needsTeamwork()) {
            desc.append(String.format("(需要%d人)", this.requiredLawyers));
        }
        if (this.isMainHandler()) {
            desc.append("[主办]");
        }
        return desc.toString();
    }

    /**
     * 办理方式分组
     */
    public enum HandleGroup {
        INDIVIDUAL("个人办理"),
        TEAM("团队办理"),
        COOPERATION("协作办理"),
        SUPERVISION("督办指导"),
        ADVISORY("顾问咨询");

        private final String description;

        HandleGroup(String description) {
            this.description = description;
        }

        public String getDescription() {
            return this.description;
        }
    }
} 