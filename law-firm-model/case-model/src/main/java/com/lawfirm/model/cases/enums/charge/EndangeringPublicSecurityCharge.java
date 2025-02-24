package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 危害公共安全罪具体罪名
 */
@Getter
public enum EndangeringPublicSecurityCharge implements BaseEnum<String> {
    // 放火、决水、爆炸等罪
    ARSON("放火罪", "020101"),
    FLOOD("决水罪", "020102"),
    EXPLOSION("爆炸罪", "020103"),
    DANGEROUS_SUBSTANCES("投放危险物质罪", "020104"),
    DANGEROUS_METHOD("以危险方法危害公共安全罪", "020105"),
    NEGLIGENT_FIRE("失火罪", "020106"),
    NEGLIGENT_FLOOD("过失决水罪", "020107"),
    NEGLIGENT_EXPLOSION("过失爆炸罪", "020108"),
    NEGLIGENT_DANGEROUS_SUBSTANCES("过失投放危险物质罪", "020109"),
    NEGLIGENT_DANGEROUS_METHOD("过失以危险方法危害公共安全罪", "020110"),

    // 破坏交通设施等罪
    SABOTAGE_VEHICLE("破坏交通工具罪", "020111"),
    SABOTAGE_TRAFFIC_FACILITY("破坏交通设施罪", "020112"),
    SABOTAGE_POWER_EQUIPMENT("破坏电力设备罪", "020113"),
    SABOTAGE_INFLAMMABLE_EQUIPMENT("破坏易燃易爆设备罪", "020114"),
    NEGLIGENT_DAMAGE_VEHICLE("过失损坏交通工具罪", "020115"),
    NEGLIGENT_DAMAGE_TRAFFIC_FACILITY("过失损坏交通设施罪", "020116"),
    NEGLIGENT_DAMAGE_POWER_EQUIPMENT("过失损坏电力设备罪", "020117"),
    NEGLIGENT_DAMAGE_INFLAMMABLE_EQUIPMENT("过失损坏易燃易爆设备罪", "020118"),

    // 恐怖活动罪
    TERRORIST_ORGANIZATION("组织、领导、参加恐怖组织罪", "020119"),
    AIDING_TERRORIST("帮助恐怖活动罪", "020120"),
    PREPARING_TERRORIST("准备实施恐怖活动罪", "020121"),
    PROPAGATING_TERRORISM("宣扬恐怖主义、极端主义、煽动实施恐怖活动罪", "020122"),
    EXTREMISM_UNDERMINING_LAW("利用极端主义破坏法律实施罪", "020123"),
    FORCING_EXTREMIST_CLOTHING("强制穿戴宣扬恐怖主义、极端主义服饰、标志罪", "020124"),
    POSSESSING_TERRORIST_MATERIALS("非法持有宣扬恐怖主义、极端主义物品罪", "020125"),

    // 劫持罪
    HIJACKING_AIRCRAFT("劫持航空器罪", "020126"),
    HIJACKING_SHIP_VEHICLE("劫持船只、汽车罪", "020127"),
    ENDANGERING_FLIGHT_SAFETY("暴力危及飞行安全罪", "020128"),

    // 破坏广播电视设施等罪
    SABOTAGE_BROADCAST_FACILITY("破坏广播电视设施、公用电信设施罪", "020129"),
    NEGLIGENT_DAMAGE_BROADCAST_FACILITY("过失损坏广播电视设施、公用电信设施罪", "020130"),

    // 非法制造、买卖、运输武器等罪
    ILLEGAL_WEAPONS("非法制造、买卖、运输、邮寄、储存枪支、弹药、爆炸物罪", "020131"),
    ILLEGAL_DANGEROUS_MATERIALS("非法制造、买卖、运输、储存危险物质罪", "020132"),
    ILLEGAL_MANUFACTURING_WEAPONS("违规制造、销售枪支罪", "020133"),
    THEFT_WEAPONS("盗窃、抢夺枪支、弹药、爆炸物、危险物质罪", "020134"),
    ROBBERY_WEAPONS("抢劫枪支、弹药、爆炸物、危险物质罪", "020135"),
    ILLEGAL_POSSESSION_WEAPONS("非法持有、私藏枪支、弹药罪", "020136"),
    ILLEGAL_LENDING_WEAPONS("非法出租、出借枪支罪", "020137"),
    UNREPORTED_LOST_WEAPONS("丢失枪支不报罪", "020138"),
    CARRYING_DANGEROUS_ITEMS("非法携带枪支、弹药、管制刀具、危险物品危及公共安全罪", "020139"),

    // 重大事故罪
    MAJOR_FLIGHT_ACCIDENT("重大飞行事故罪", "020140"),
    RAILWAY_SAFETY_ACCIDENT("铁路运营安全事故罪", "020141"),
    TRAFFIC_ACCIDENT("交通肇事罪", "020142"),
    DANGEROUS_DRIVING("危险驾驶罪", "020143"),
    INTERFERING_SAFE_DRIVING("妨害安全驾驶罪", "020144"),
    MAJOR_LIABILITY_ACCIDENT("重大责任事故罪", "020145"),
    DANGEROUS_OPERATION("危险作业罪", "020146"),
    FORCING_DANGEROUS_OPERATION("强令、组织违章冒险作业罪", "020147"),
    MAJOR_LABOR_SAFETY_ACCIDENT("重大劳动安全事故罪", "020148"),
    MASS_ACTIVITY_ACCIDENT("大型群众性活动重大安全事故罪", "020149"),
    DANGEROUS_MATERIALS_ACCIDENT("危险物品肇事罪", "020150"),
    CONSTRUCTION_SAFETY_ACCIDENT("工程重大安全事故罪", "020151"),
    EDUCATIONAL_FACILITY_ACCIDENT("教育设施重大安全事故罪", "020152"),
    FIRE_SAFETY_ACCIDENT("消防责任事故罪", "020153"),
    UNREPORTED_SAFETY_ACCIDENT("不报、谎报安全事故罪", "020154");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.ENDANGERING_PUBLIC_SECURITY;

    EndangeringPublicSecurityCharge(String description, String code) {
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