package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 扰乱公共秩序罪
 */
@Getter
public enum DisturbingPublicOrderCharge implements BaseEnum<String> {
    OBSTRUCTING_OFFICIAL_DUTIES("妨害公务罪", "060101"),
    ASSAULTING_POLICE("袭警罪", "060102"),
    INCITING_VIOLENCE("煽动暴力抗拒法律实施罪", "060103"),
    FRAUD_IMPERSONATION("招摇撞骗罪", "060104"),
    FORGING_OFFICIAL_DOCUMENTS("伪造、变造、买卖国家机关公文、证件、印章罪", "060105"),
    STEALING_OFFICIAL_DOCUMENTS("盗窃、抢夺、毁灭国家机关公文、证件、印章罪", "060106"),
    FORGING_COMPANY_SEALS("伪造公司、企业、事业单位、人民团体印章罪", "060107"),
    FORGING_ID_DOCUMENTS("伪造、变造、买卖身份证件罪", "060108"),
    USING_FALSE_ID("使用虚假身份证件、盗用身份证件罪", "060109"),
    IDENTITY_THEFT("冒名顶替罪", "060110"),
    ILLEGAL_POLICE_EQUIPMENT("非法生产、买卖警用装备罪", "060111"),
    ILLEGAL_STATE_SECRETS("非法获取国家秘密罪", "060112"),
    ILLEGAL_POSSESSION_SECRETS("非法持有国家绝密、机密文件、资料、物品罪", "060113"),
    ILLEGAL_SPY_EQUIPMENT("非法生产、销售专用间谍器材、窃听、窃照专用器材罪", "060114"),
    ILLEGAL_USE_SPY_EQUIPMENT("非法使用窃听、窃照专用器材罪", "060115"),
    ORGANIZING_EXAM_CHEATING("组织考试作弊罪", "060116"),
    SELLING_EXAM_ANSWERS("非法出售、提供试题、答案罪", "060117"),
    EXAM_IMPERSONATION("代替考试罪", "060118"),
    ILLEGAL_COMPUTER_ACCESS("非法侵入计算机信息系统罪", "060119"),
    ILLEGAL_DATA_ACCESS("非法获取计算机信息系统数据、非法控制计算机信息系统罪", "060120"),
    PROVIDING_INTRUSION_TOOLS("提供侵入、非法控制计算机信息系统程序、工具罪", "060121"),
    DAMAGING_COMPUTER_SYSTEMS("破坏计算机信息系统罪", "060122"),
    NETWORK_SECURITY_NEGLIGENCE("拒不履行信息网络安全管理义务罪", "060123"),
    ILLEGAL_NETWORK_USE("非法利用信息网络罪", "060124"),
    ASSISTING_NETWORK_CRIMES("帮助信息网络犯罪活动罪", "060125"),
    DISRUPTING_RADIO_COMMUNICATIONS("扰乱无线电通讯管理秩序罪", "060126"),
    GATHERING_CROWD_DISTURBANCE("聚众扰乱社会秩序罪", "060127"),
    ATTACKING_STATE_ORGANS("聚众冲击国家机关罪", "060128"),
    DISRUPTING_STATE_ORGANS("扰乱国家机关工作秩序罪", "060129"),
    ORGANIZING_ILLEGAL_GATHERING("组织、资助非法聚集罪", "060130"),
    DISRUPTING_PUBLIC_PLACE("聚众扰乱公共场所秩序、交通秩序罪", "060131"),
    FALSE_DANGEROUS_MATERIALS("投放虚假危险物质罪", "060132"),
    FALSE_TERRORISM_INFO("编造、故意传播虚假恐怖信息罪", "060133"),
    SPREADING_FALSE_INFO("编造、故意传播虚假信息罪", "060134"),
    THROWING_OBJECTS("高空抛物罪", "060135"),
    GROUP_FIGHTING("聚众斗殴罪", "060136"),
    PICKING_QUARRELS("寻衅滋事罪", "060137"),
    ILLEGAL_DEBT_COLLECTION("催收非法债务罪", "060138"),
    ORGANIZING_CRIMINAL_GANG("组织、领导、参加黑社会性质组织罪", "060139"),
    FOREIGN_CRIMINAL_GANG("入境发展黑社会组织罪", "060140"),
    HARBORING_CRIMINAL_GANG("包庇、纵容黑社会性质组织罪", "060141"),
    TEACHING_CRIME_METHODS("传授犯罪方法罪", "060142"),
    ILLEGAL_ASSEMBLY("非法集会、游行、示威罪", "060143"),
    ILLEGAL_WEAPONS_ASSEMBLY("非法携带武器、管制刀具、爆炸物参加集会、游行、示威罪", "060144"),
    DISRUPTING_ASSEMBLY("破坏集会、游行、示威罪", "060145"),
    INSULTING_NATIONAL_SYMBOLS("侮辱国旗、国徽、国歌罪", "060146"),
    DEFAMING_HEROES("侵害英雄烈士名誉、荣誉罪", "060147"),
    ORGANIZING_CULT("组织、利用会道门、邪教组织、利用迷信破坏法律实施罪", "060148"),
    CULT_CAUSING_DEATH("组织、利用会道门、邪教组织、利用迷信致人重伤、死亡罪", "060149"),
    GROUP_LICENTIOUSNESS("聚众淫乱罪", "060150"),
    LURING_MINORS_LICENTIOUSNESS("引诱未成年人聚众淫乱罪", "060151"),
    INSULTING_CORPSE("盗窃、侮辱、故意毁坏尸体、尸骨、骨灰罪", "060152"),
    GAMBLING("赌博罪", "060153"),
    OPERATING_GAMBLING_HOUSE("开设赌场罪", "060154"),
    ORGANIZING_OVERSEAS_GAMBLING("组织参与国（境）外赌博罪", "060155"),
    DELAYING_POST("故意延误投递邮件罪", "060156");

    private final String description;
    private final String code;
    private final CriminalCharge.Second parentCharge = CriminalCharge.Second.DISTURBING_PUBLIC_ORDER;

    DisturbingPublicOrderCharge(String description, String code) {
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