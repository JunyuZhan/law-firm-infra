package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 妨害文物管理罪
 */
@Getter
public enum CulturalRelicCharge implements BaseEnum<String> {
    INTENTIONAL_DAMAGE_RELIC("故意损毁文物罪", "060401"),
    INTENTIONAL_DAMAGE_SITE("故意损毁名胜古迹罪", "060402"),
    NEGLIGENT_DAMAGE_RELIC("过失损毁文物罪", "060403"),
    SELLING_RELICS_ABROAD("非法向外国人出售、赠送珍贵文物罪", "060404"),
    RELIC_TRAFFICKING("倒卖文物罪", "060405"),
    ILLEGAL_SALE_COLLECTION("非法出售、私赠文物藏品罪", "060406"),
    EXCAVATING_SITES("盗掘古文化遗址、古墓葬罪", "060407"),
    EXCAVATING_FOSSILS("盗掘古人类化石、古脊椎动物化石罪", "060408"),
    STEALING_ARCHIVES("抢夺、窃取国有档案罪", "060409"),
    SELLING_ARCHIVES("擅自出卖、转让国有档案罪", "060410");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.CULTURAL_RELIC;

    CulturalRelicCharge(String description, String code) {
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