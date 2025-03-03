package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 费用类型枚举
 */
@Getter
public enum FeeTypeEnum implements BaseEnum<String> {
    
    LEGAL_SERVICE("LEGAL_SERVICE", "法律服务费"),
    CONSULTATION("CONSULTATION", "法律咨询费"),
    RETAINER("RETAINER", "法律顾问费"),
    LITIGATION("LITIGATION", "诉讼费用"),
    ARBITRATION("ARBITRATION", "仲裁费用"),
    DOCUMENT("DOCUMENT", "文书费用"),
    NOTARIZATION("NOTARIZATION", "公证费用"),
    TRANSLATION("TRANSLATION", "翻译费用"),
    TRAVEL("TRAVEL", "差旅费用"),
    OTHER("OTHER", "其他费用");

    private final String value;
    private final String description;

    FeeTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 