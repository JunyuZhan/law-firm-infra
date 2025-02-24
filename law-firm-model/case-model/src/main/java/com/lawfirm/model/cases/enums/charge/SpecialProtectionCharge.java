package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 特殊保护对象罪
 */
@Getter
public enum SpecialProtectionCharge implements BaseEnum<String> {
    CHILD_ABUSE("虐待罪", "040501"),
    ELDER_ABUSE("虐待被监护、看护人罪", "040502"),
    ABANDONMENT("遗弃罪", "040503"),
    CHILD_TRAFFICKING("拐骗儿童罪", "040504"),
    CHILD_BEGGING("雇用未成年人乞讨罪", "040505"),
    CHILD_LABOR("使用童工从事危重劳动罪", "040506"),
    CHILD_MOLESTATION("猥亵儿童罪", "040507"),
    CHILD_PORNOGRAPHY("制作、传播儿童色情制品罪", "040508");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.SPECIAL_PROTECTION;

    SpecialProtectionCharge(String description, String code) {
        this.description = description;
        this.code = code;
    }

    @Override
    public String getValue() {
        return this.code;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 