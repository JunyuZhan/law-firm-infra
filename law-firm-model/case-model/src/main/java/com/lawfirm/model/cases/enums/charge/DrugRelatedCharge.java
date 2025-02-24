package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 走私、贩卖、运输、制造毒品罪
 */
@Getter
public enum DrugRelatedCharge implements BaseEnum<String> {
    DRUG_TRAFFICKING("走私、贩卖、运输、制造毒品罪", "060701"),
    ILLEGAL_POSSESSION("非法持有毒品罪", "060702"),
    HARBORING_DRUG_CRIMINALS("包庇毒品犯罪分子罪", "060703"),
    CONCEALING_DRUG_PROCEEDS("窝藏、转移、隐瞒毒品、毒赃罪", "060704"),
    ILLEGAL_PRECURSOR_CHEMICALS("非法生产、买卖、运输制毒物品、走私制毒物品罪", "060705"),
    ILLEGAL_DRUG_PLANTS("非法种植毒品原植物罪", "060706"),
    ILLEGAL_DRUG_SEEDS("非法买卖、运输、携带、持有毒品原植物种子、幼苗罪", "060707"),
    INDUCING_DRUG_USE("引诱、教唆、欺骗他人吸毒罪", "060708"),
    FORCED_DRUG_USE("强迫他人吸毒罪", "060709"),
    PROVIDING_VENUE("容留他人吸毒罪", "060710"),
    ILLEGAL_DRUG_SUPPLY("非法提供麻醉药品、精神药品罪", "060711"),
    DOPING_MANAGEMENT("妨害兴奋剂管理罪", "060712");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.DRUG_RELATED;

    DrugRelatedCharge(String description, String code) {
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