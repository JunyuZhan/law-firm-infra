package com.lawfirm.model.archive.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 档案类型枚举
 */
@Getter
@AllArgsConstructor
public enum ArchiveTypeEnum {
    
    CASE(1, "案件档案"),
    CONTRACT(2, "合同档案"),
    DOCUMENT(3, "文书档案"),
    FINANCIAL(4, "财务档案"),
    PERSONNEL(5, "人事档案"),
    OTHER(99, "其他档案");
    
    @EnumValue
    @JsonValue
    private final Integer code;
    
    private final String desc;
} 