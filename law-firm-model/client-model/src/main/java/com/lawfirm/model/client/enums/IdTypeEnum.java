package com.lawfirm.model.client.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum IdTypeEnum implements BaseEnum<String> {
    
    ID_CARD("ID_CARD", "身份证"),
    PASSPORT("PASSPORT", "护照"),
    BUSINESS_LICENSE("BUSINESS_LICENSE", "营业执照"),
    ORGANIZATION_CODE("ORGANIZATION_CODE", "组织机构代码"),
    SOCIAL_CREDIT_CODE("SOCIAL_CREDIT_CODE", "统一社会信用代码"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    IdTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 