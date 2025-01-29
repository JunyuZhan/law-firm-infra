package com.lawfirm.model.cases.enums;

import lombok.Getter;

@Getter
public enum CaseSourceEnum {
    
    DIRECT("direct", "直接委托"),
    REFERRAL("referral", "转介绍"),
    BIDDING("bidding", "投标"),
    GOVERNMENT("government", "政府指派"),
    PLATFORM("platform", "平台获取"),
    OTHER("other", "其他");
    
    private final String code;
    private final String description;
    
    CaseSourceEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
} 