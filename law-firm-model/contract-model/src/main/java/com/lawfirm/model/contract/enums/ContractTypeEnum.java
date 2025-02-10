package com.lawfirm.model.contract.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

/**
 * 合同类型枚举
 */
@Getter
public enum ContractTypeEnum {
    
    LEGAL_SERVICE("LEGAL_SERVICE", "法律服务合同"),
    RETAINER("RETAINER", "法律顾问合同"),
    CASE("CASE", "诉讼代理合同"),
    ARBITRATION("ARBITRATION", "仲裁代理合同"),
    CONSULTATION("CONSULTATION", "法律咨询合同"),
    COOPERATION("COOPERATION", "合作协议"),
    EMPLOYMENT("EMPLOYMENT", "聘用合同"),
    PROCUREMENT("PROCUREMENT", "采购合同"),
    LEASE("LEASE", "租赁合同"),
    OTHER("OTHER", "其他合同");

    @EnumValue
    @JsonValue
    private final String code;
    private final String description;

    ContractTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }
}

