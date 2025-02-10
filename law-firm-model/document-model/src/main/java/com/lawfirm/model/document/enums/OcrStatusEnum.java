package com.lawfirm.model.document.enums;

import com.lawfirm.common.core.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * OCR状态枚举
 */
@Getter
@AllArgsConstructor
public enum OcrStatusEnum implements BaseEnum<String> {
    
    NOT_STARTED("NOT_STARTED", "未开始"),
    PROCESSING("PROCESSING", "处理中"),
    COMPLETED("COMPLETED", "已完成"),
    FAILED("FAILED", "失败");

    private final String code;
    private final String description;

    @Override
    public String getValue() {
        return code;
    }
} 