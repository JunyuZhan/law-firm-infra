package com.lawfirm.model.client.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

@Getter
public enum ClientSourceEnum implements BaseEnum<String> {
    
    REFERRAL("REFERRAL", "转介绍"),
    ONLINE("ONLINE", "线上获取"),
    MARKETING("MARKETING", "市场营销"),
    EXISTING("EXISTING", "老客户"),
    PARTNER("PARTNER", "合作伙伴"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String description;

    ClientSourceEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }
} 