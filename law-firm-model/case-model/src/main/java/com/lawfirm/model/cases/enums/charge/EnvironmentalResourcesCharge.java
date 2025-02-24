package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 破坏环境资源保护罪
 */
@Getter
public enum EnvironmentalResourcesCharge implements BaseEnum<String> {
    POLLUTING_ENVIRONMENT("污染环境罪", "060601"),
    ILLEGAL_WASTE_DISPOSAL("非法处置进口的固体废物罪", "060602"),
    ILLEGAL_WASTE_IMPORT("擅自进口固体废物罪", "060603"),
    ILLEGAL_FISHING("非法捕捞水产品罪", "060604"),
    ENDANGERING_WILDLIFE("危害珍贵、濒危野生动物罪", "060605"),
    ILLEGAL_HUNTING("非法狩猎罪", "060606"),
    ILLEGAL_WILDLIFE_TRADE("非法猎捕、收购、运输、出售陆生野生动物罪", "060607"),
    ILLEGAL_FARMLAND_USE("非法占用农用地罪", "060608"),
    DAMAGING_PROTECTED_AREAS("破坏自然保护地罪", "060609"),
    ILLEGAL_MINING("非法采矿罪", "060610"),
    DESTRUCTIVE_MINING("破坏性采矿罪", "060611"),
    ENDANGERING_PLANTS("危害国家重点保护植物罪", "060612"),
    INVASIVE_SPECIES("非法引进、释放、丢弃外来入侵物种罪", "060613"),
    ILLEGAL_LOGGING("盗伐林木罪", "060614"),
    EXCESSIVE_LOGGING("滥伐林木罪", "060615"),
    ILLEGAL_TIMBER_TRADE("非法收购、运输盗伐、滥伐的林木罪", "060616");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.ENVIRONMENTAL_RESOURCES;

    EnvironmentalResourcesCharge(String description, String code) {
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