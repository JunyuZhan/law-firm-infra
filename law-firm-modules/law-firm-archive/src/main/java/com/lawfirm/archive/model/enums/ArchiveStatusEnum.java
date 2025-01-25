package com.lawfirm.archive.model.enums;

import lombok.Getter;

/**
 * 档案状态枚举
 */
@Getter
public enum ArchiveStatusEnum {
    
    IN_STORAGE("IN_STORAGE", "在库"),
    BORROWED("BORROWED", "借出"),
    DESTROYED("DESTROYED", "销毁");
    
    private final String code;
    private final String desc;
    
    ArchiveStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }
} 