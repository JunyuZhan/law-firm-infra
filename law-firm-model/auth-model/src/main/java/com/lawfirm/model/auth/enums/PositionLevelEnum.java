package com.lawfirm.model.auth.enums;

import lombok.Getter;
import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 职位级别枚举
 */
@Getter
public enum PositionLevelEnum implements BaseEnum<Integer> {
    
    /**
     * 高级合伙人
     */
    SENIOR_PARTNER(0, "高级合伙人"),
    
    /**
     * 合伙人
     */
    PARTNER(1, "合伙人"),
    
    /**
     * 高级律师
     */
    SENIOR_LAWYER(2, "高级律师"),
    
    /**
     * 资深律师
     */
    EXPERIENCED_LAWYER(3, "资深律师"),
    
    /**
     * 专职律师
     */
    FULL_TIME_LAWYER(4, "专职律师"),
    
    /**
     * 实习律师
     */
    TRAINEE_LAWYER(5, "实习律师"),
    
    /**
     * 律师助理
     */
    LEGAL_ASSISTANT(6, "律师助理"),
    
    /**
     * 行政总监
     */
    ADMIN_DIRECTOR(7, "行政总监"),
    
    /**
     * 财务总监
     */
    FINANCE_DIRECTOR(8, "财务总监"),
    
    /**
     * 人力资源总监
     */
    HR_DIRECTOR(9, "人力资源总监"),
    
    /**
     * IT总监
     */
    IT_DIRECTOR(10, "IT总监"),
    
    /**
     * 行政主管
     */
    ADMIN_MANAGER(11, "行政主管"),
    
    /**
     * 财务主管
     */
    FINANCE_MANAGER(12, "财务主管"),
    
    /**
     * 人力资源主管
     */
    HR_MANAGER(13, "人力资源主管"),
    
    /**
     * 行政专员
     */
    ADMIN_SPECIALIST(14, "行政专员"),
    
    /**
     * 财务专员
     */
    FINANCE_SPECIALIST(15, "财务专员"),
    
    /**
     * 人力资源专员
     */
    HR_SPECIALIST(16, "人力资源专员");

    private final Integer code;
    private final String desc;
    
    PositionLevelEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return code;
    }

    @Override
    public String getDescription() {
        return desc;
    }
} 