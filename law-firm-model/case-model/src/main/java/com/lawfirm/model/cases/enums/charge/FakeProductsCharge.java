package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 生产、销售伪劣商品罪
 */
@Getter
public enum FakeProductsCharge implements BaseEnum<String> {
    FAKE_PRODUCTS("生产、销售伪劣产品罪", "030101"),
    FAKE_MEDICINE("生产、销售、提供假药罪", "030102"),
    SUBSTANDARD_MEDICINE("生产、销售、提供劣药罪", "030103"),
    MEDICINE_MANAGEMENT("妨害药品管理罪", "030104"),
    UNSAFE_FOOD("生产、销售不符合安全标准的食品罪", "030105"),
    POISONOUS_FOOD("生产、销售有毒、有害食品罪", "030106"),
    UNSAFE_MEDICAL_EQUIPMENT("生产、销售不符合标准的医用器材罪", "030107"),
    UNSAFE_PRODUCTS("生产、销售不符合安全标准的产品罪", "030108"),
    FAKE_AGRICULTURAL_PRODUCTS("生产、销售伪劣农药、兽药、化肥、种子罪", "030109"),
    UNSAFE_COSMETICS("生产、销售不符合卫生标准的化妆品罪", "030110");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.FAKE_PRODUCTS;

    FakeProductsCharge(String description, String code) {
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