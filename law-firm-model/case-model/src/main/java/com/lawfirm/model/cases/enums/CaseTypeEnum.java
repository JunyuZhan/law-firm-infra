package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案件类型枚举
 */
@Getter
public enum CaseTypeEnum implements BaseEnum<String> {
    
    // 民事案件
    CIVIL("民事案件"),
    
    // 刑事案件
    CRIMINAL("刑事案件"),
    
    // 行政案件
    ADMINISTRATIVE("行政案件"),
    
    // 国家赔偿案件
    STATE_COMPENSATION("国家赔偿案件"),
    
    // 执行案件
    EXECUTION("执行案件"),
    
    // 非诉讼案件
    NON_LITIGATION("非诉讼案件"),
    
    // 特别程序案件
    SPECIAL_PROCEDURE("特别程序案件"),
    
    // 仲裁案件
    ARBITRATION("仲裁案件"),
    
    // 调解案件
    MEDIATION("调解案件"),
    
    // 涉外案件
    FOREIGN_RELATED("涉外案件"),
    
    // 知识产权案件
    INTELLECTUAL_PROPERTY("知识产权案件"),
    
    // 海事海商案件
    MARITIME("海事海商案件"),
    
    // 破产案件
    BANKRUPTCY("破产案件"),
    
    // 其他案件
    OTHER("其他案件");

    private final String description;

    CaseTypeEnum(String description) {
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 