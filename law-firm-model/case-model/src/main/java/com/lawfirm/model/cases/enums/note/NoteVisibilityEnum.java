package com.lawfirm.model.cases.enums.note;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 笔记可见性枚举
 */
@Getter
public enum NoteVisibilityEnum implements BaseEnum<Integer> {

    /**
     * 私人
     * 仅创建者可见
     */
    PRIVATE(1, "私人"),

    /**
     * 团队
     * 案件团队成员可见
     */
    TEAM(2, "团队"),

    /**
     * 公开
     * 所有律师可见
     */
    PUBLIC(3, "公开"),

    /**
     * 指定人员
     * 仅指定的人员可见
     */
    SPECIFIC_USERS(4, "指定人员");

    private final Integer value;
    private final String description;

    NoteVisibilityEnum(Integer value, String description) {
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
    public static NoteVisibilityEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (NoteVisibilityEnum visibility : values()) {
            if (visibility.value.equals(value)) {
                return visibility;
            }
        }
        return null;
    }

    /**
     * 是否需要指定可见人员
     */
    public boolean needSpecifyUsers() {
        return this == SPECIFIC_USERS;
    }

    /**
     * 是否仅创建者可见
     */
    public boolean isOnlyCreatorVisible() {
        return this == PRIVATE;
    }

    /**
     * 是否团队可见
     */
    public boolean isTeamVisible() {
        return this == TEAM;
    }

    /**
     * 是否公开可见
     */
    public boolean isPublicVisible() {
        return this == PUBLIC;
    }

    /**
     * 获取可见性描述
     */
    public String getVisibilityDescription() {
        switch (this) {
            case PRIVATE:
                return "仅创建者可见";
            case TEAM:
                return "案件团队成员可见";
            case PUBLIC:
                return "所有律师可见";
            case SPECIFIC_USERS:
                return "仅指定的人员可见";
            default:
                return "";
        }
    }

    /**
     * 是否需要确认查看权限
     */
    public boolean needVerifyPermission() {
        return this != PUBLIC;
    }

    /**
     * 是否需要记录查看日志
     */
    public boolean needAccessLog() {
        return this == PRIVATE || this == SPECIFIC_USERS;
    }
} 