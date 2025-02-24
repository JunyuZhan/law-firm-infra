package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 制作、传播淫秽物品罪
 */
@Getter
public enum PornographyCharge implements BaseEnum<String> {
    PRODUCING_PORNOGRAPHY("制作、复制、出版、贩卖、传播淫秽物品牟利罪", "060901"),
    DISSEMINATING_PORNOGRAPHY("传播淫秽物品罪", "060902"),
    ORGANIZING_PORNOGRAPHY("组织播放淫秽音像制品罪", "060903"),
    ORGANIZING_OBSCENE_PERFORMANCE("组织淫秽表演罪", "060904");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.PORNOGRAPHY;

    PornographyCharge(String description, String code) {
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