package com.lawfirm.model.cases.enums.charge;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 刑事罪名
 */
public class CriminalCharge {

    /**
     * 刑事罪名一级分类
     */
    @Getter
    public enum First implements BaseEnum<String> {
        ENDANGERING_STATE_SECURITY("危害国家安全罪", "01"),
        ENDANGERING_PUBLIC_SECURITY("危害公共安全罪", "02"),
        DISRUPTING_MARKET_ORDER("破坏社会主义市场经济秩序罪", "03"),
        VIOLATING_CIVIL_RIGHTS("侵犯公民人身权利、民主权利罪", "04"),
        VIOLATING_PROPERTY("侵犯财产罪", "05"),
        DISRUPTING_SOCIAL_ORDER("妨害社会管理秩序罪", "06"),
        ENDANGERING_NATIONAL_DEFENSE("危害国防利益罪", "07"),
        CORRUPTION_BRIBERY("贪污贿赂罪", "08"),
        DERELICTION_OF_DUTY("渎职罪", "09"),
        MILITARY_DUTY_CRIMES("军人违反职责罪", "10");

        private final String description;
        private final String code;

        First(String description, String code) {
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

    /**
     * 刑事罪名二级分类
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        // 危害国家安全罪
        ENDANGERING_STATE_SECURITY("危害国家安全罪", "0101"),
        
        // 危害公共安全罪
        ENDANGERING_PUBLIC_SECURITY("危害公共安全罪", "0201"),

        // 破坏社会主义市场经济秩序罪
        FAKE_PRODUCTS("生产、销售伪劣商品罪", "0301"),
        SMUGGLING("走私罪", "0302"),
        COMPANY_MANAGEMENT("妨害对公司、企业的管理秩序罪", "0303"),
        FINANCIAL_MANAGEMENT("破坏金融管理秩序罪", "0304"),
        FINANCIAL_FRAUD("金融诈骗罪", "0305"),
        TAX_COLLECTION("危害税收征管罪", "0306"),
        INTELLECTUAL_PROPERTY("侵犯知识产权罪", "0307"),
        MARKET_ORDER("扰乱市场秩序罪", "0308"),

        // 侵犯公民人身权利、民主权利罪
        LIFE_HEALTH("侵犯生命健康权利罪", "0401"),
        PERSONAL_FREEDOM("侵犯人身自由权利罪", "0402"),
        PERSONAL_DIGNITY("侵犯人格尊严权利罪", "0403"),
        DEMOCRATIC_RIGHTS("侵犯民主权利罪", "0404"),
        SPECIAL_PROTECTION("特殊人群保护罪", "0405"),

        // 侵犯财产罪
        VIOLENT_PROPERTY("暴力侵犯财产罪", "0501"),
        NON_VIOLENT_PROPERTY("非暴力侵犯财产罪", "0502"),
        OCCUPATION_PROPERTY("职务侵犯财产罪", "0503"),

        // 妨害社会管理秩序罪
        DISTURBING_PUBLIC_ORDER("扰乱公共秩序罪", "0601"),
        OBSTRUCTING_JUSTICE("妨害司法罪", "0602"),
        BORDER_CONTROL("妨害国（边）境管理罪", "0603"),
        CULTURAL_RELIC("妨害文物管理罪", "0604"),
        PUBLIC_HEALTH("危害公共卫生罪", "0605"),
        ENVIRONMENTAL_RESOURCES("破坏环境资源保护罪", "0606"),
        DRUG_RELATED("走私、贩卖、运输、制造毒品罪", "0607"),
        PROSTITUTION("组织、强迫、引诱、容留、介绍卖淫罪", "0608"),
        PORNOGRAPHY("制作、贩卖、传播淫秽物品罪", "0609"),

        // 危害国防利益罪
        MILITARY_SERVICE("妨害军事服务罪", "0701"),
        MILITARY_FACILITIES("破坏军事设施罪", "0702"),
        MILITARY_ORDER("扰乱军事秩序罪", "0703"),
        WARTIME_OFFENSES("战时犯罪", "0704"),

        // 贪污贿赂罪
        EMBEZZLEMENT("贪污罪", "0801"),
        BRIBERY("受贿罪", "0802"),
        UNEXPLAINED_ASSETS("巨额财产来源不明罪", "0803"),
        DIVIDING_STATE_ASSETS("私分国有资产罪", "0804"),

        // 渎职罪
        ABUSE_OF_POWER("滥用职权罪", "0901"),
        DERELICTION_DUTY("玩忽职守罪", "0902"),
        LEAKING_STATE_SECRETS("泄露国家秘密罪", "0903"),
        JUDICIAL_MISCONDUCT("枉法裁判罪", "0904"),
        RELEASING_PRISONERS("私放在押人员罪", "0905"),
        REGULATORY_NEGLIGENCE("监管渎职罪", "0906"),
        ADMINISTRATIVE_MISCONDUCT("行政渎职罪", "0907"),

        // 军人违反职责罪
        COMBAT_DUTY("作战职责罪", "1001"),
        MILITARY_SECRETS("军事秘密罪", "1002"),
        MILITARY_EQUIPMENT("武器装备罪", "1003"),
        MILITARY_PERSONNEL("军人人身罪", "1004"),
        PRISONER_OF_WAR("战俘罪", "1005");

        private final String description;
        private final String code;
        private final First parentCharge;

        Second(String description, String code) {
            this.description = description;
            this.code = code;
            if (code.startsWith("01")) {
                this.parentCharge = First.ENDANGERING_STATE_SECURITY;
            } else if (code.startsWith("02")) {
                this.parentCharge = First.ENDANGERING_PUBLIC_SECURITY;
            } else if (code.startsWith("03")) {
                this.parentCharge = First.DISRUPTING_MARKET_ORDER;
            } else if (code.startsWith("04")) {
                this.parentCharge = First.VIOLATING_CIVIL_RIGHTS;
            } else if (code.startsWith("05")) {
                this.parentCharge = First.VIOLATING_PROPERTY;
            } else if (code.startsWith("06")) {
                this.parentCharge = First.DISRUPTING_SOCIAL_ORDER;
            } else if (code.startsWith("07")) {
                this.parentCharge = First.ENDANGERING_NATIONAL_DEFENSE;
            } else if (code.startsWith("08")) {
                this.parentCharge = First.CORRUPTION_BRIBERY;
            } else if (code.startsWith("09")) {
                this.parentCharge = First.DERELICTION_OF_DUTY;
            } else {
                this.parentCharge = First.MILITARY_DUTY_CRIMES;
            }
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
} 