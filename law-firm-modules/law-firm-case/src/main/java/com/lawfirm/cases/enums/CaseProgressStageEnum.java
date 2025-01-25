package com.lawfirm.cases.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 案件进展阶段枚举
 */
@Getter
public enum CaseProgressStageEnum {
    
    FILING(1, "立案"),
    PREPARATION(2, "庭前准备"),
    COURT(3, "开庭"),
    JUDGMENT(4, "判决"),
    EXECUTION(5, "执行");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;
    
    CaseProgressStageEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 