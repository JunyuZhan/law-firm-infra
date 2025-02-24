package com.lawfirm.model.cases.enums.reminder;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 重复类型枚举
 */
@Getter
public enum RepeatTypeEnum implements BaseEnum<Integer> {

    /**
     * 不重复
     */
    NONE(1, "不重复"),

    /**
     * 每天
     */
    DAILY(2, "每天"),

    /**
     * 每周
     */
    WEEKLY(3, "每周"),

    /**
     * 每月
     */
    MONTHLY(4, "每月"),

    /**
     * 每季度
     */
    QUARTERLY(5, "每季度"),

    /**
     * 每年
     */
    YEARLY(6, "每年"),

    /**
     * 工作日
     */
    WORKDAYS(7, "工作日"),

    /**
     * 自定义间隔（天）
     */
    CUSTOM_DAYS(8, "自定义间隔（天）"),

    /**
     * 自定义间隔（小时）
     */
    CUSTOM_HOURS(9, "自定义间隔（小时）");

    private final Integer value;
    private final String description;

    RepeatTypeEnum(Integer value, String description) {
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
    public static RepeatTypeEnum valueOf(Integer value) {
        if (value == null) {
            return null;
        }
        for (RepeatTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }

    /**
     * 是否需要指定具体时间
     */
    public boolean needSpecificTime() {
        return this != NONE && this != CUSTOM_HOURS;
    }

    /**
     * 是否需要指定间隔值
     */
    public boolean needIntervalValue() {
        return this == CUSTOM_DAYS || this == CUSTOM_HOURS;
    }

    /**
     * 是否需要考虑节假日
     */
    public boolean needHolidayConsideration() {
        return this == WORKDAYS || this == DAILY;
    }

    /**
     * 是否需要指定重复次数
     */
    public boolean needRepeatCount() {
        return this != NONE;
    }

    /**
     * 获取默认间隔值
     */
    public int getDefaultInterval() {
        switch (this) {
            case DAILY:
            case WORKDAYS:
                return 1;
            case WEEKLY:
                return 7;
            case MONTHLY:
                return 30;
            case QUARTERLY:
                return 90;
            case YEARLY:
                return 365;
            case CUSTOM_DAYS:
                return 1;
            case CUSTOM_HOURS:
                return 24;
            default:
                return 0;
        }
    }

    /**
     * 获取最大间隔值
     */
    public int getMaxInterval() {
        switch (this) {
            case CUSTOM_DAYS:
                return 365;
            case CUSTOM_HOURS:
                return 168; // 一周的小时数
            default:
                return 0;
        }
    }

    /**
     * 获取建议重复次数
     */
    public int getSuggestedRepeatCount() {
        switch (this) {
            case DAILY:
            case WORKDAYS:
                return 30;
            case WEEKLY:
                return 12;
            case MONTHLY:
                return 12;
            case QUARTERLY:
                return 4;
            case YEARLY:
                return 1;
            default:
                return 1;
        }
    }

    /**
     * 是否支持无限重复
     */
    public boolean supportInfiniteRepeat() {
        return this != CUSTOM_DAYS && this != CUSTOM_HOURS;
    }
} 