package com.lawfirm.archive.model.enums;

import lombok.Getter;

/**
 * 档案借阅状态枚举
 */
@Getter
public enum BorrowStatusEnum {
    
    BORROWED("BORROWED", "借出"),
    RETURNED("RETURNED", "已归还"),
    OVERDUE("OVERDUE", "超期");
    
    private final String code;
    private final String desc;
    
    BorrowStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 