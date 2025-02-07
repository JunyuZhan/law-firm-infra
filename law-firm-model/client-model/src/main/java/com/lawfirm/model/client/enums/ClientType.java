package com.lawfirm.model.client.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum ClientType {
    
    PERSONAL(1, "个人"),
    ENTERPRISE(2, "企业");
    
    @EnumValue
    @JsonValue
    private final Integer value;
    
    private final String desc;
    
    ClientType(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
} 