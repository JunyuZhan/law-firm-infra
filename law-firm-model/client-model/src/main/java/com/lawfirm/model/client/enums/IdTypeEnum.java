package com.lawfirm.model.client.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum IdTypeEnum implements BaseEnum<String> {
    
    ID_CARD("ID_CARD", "居民身份证"),
    PASSPORT("PASSPORT", "护照"),
    BUSINESS_LICENSE("BUSINESS_LICENSE", "营业执照"),
    ORG_CODE("ORG_CODE", "组织机构代码证"),
    SOCIAL_CREDIT("SOCIAL_CREDIT", "统一社会信用代码证"),
    OTHER("OTHER", "其他证件");

    private final String value;
    private final String description;

    IdTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 