package com.lawfirm.cases.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 案件文档类型枚举
 */
@Getter
public enum DocumentTypeEnum {
    
    COMPLAINT(1, "起诉状"),
    DEFENSE(2, "答辩状"),
    EVIDENCE(3, "证据材料"),
    JUDGMENT(4, "判决书"),
    OTHER(5, "其他");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    private final String desc;
    
    DocumentTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 