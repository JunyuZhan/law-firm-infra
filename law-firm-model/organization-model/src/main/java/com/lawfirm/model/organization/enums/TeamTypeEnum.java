package com.lawfirm.model.organization.enums;

import com.lawfirm.model.base.enums.BaseEnum;

/**
 * 团队类型枚举
 */
public enum TeamTypeEnum implements BaseEnum<Integer> {
    /**
     * 民商事诉讼团队
     */
    CIVIL_LITIGATION(1, "民商事诉讼团队"),

    /**
     * 刑事诉讼团队
     */
    CRIMINAL_LITIGATION(2, "刑事诉讼团队"),

    /**
     * 行政诉讼团队
     */
    ADMINISTRATIVE_LITIGATION(3, "行政诉讼团队"),

    /**
     * 仲裁团队
     */
    ARBITRATION(4, "仲裁团队"),

    /**
     * 争议解决团队
     */
    DISPUTE_RESOLUTION(5, "争议解决团队"),

    /**
     * 合同法律团队
     */
    CONTRACT(6, "合同法律团队"),

    /**
     * 公司法律顾问团队
     */
    CORPORATE_COUNSEL(7, "公司法律顾问团队"),

    /**
     * 并购重组团队
     */
    MERGER_ACQUISITION(8, "并购重组团队"),

    /**
     * 投融资团队
     */
    INVESTMENT_FINANCING(9, "投融资团队"),

    /**
     * 证券资本市场团队
     */
    SECURITIES_CAPITAL_MARKET(10, "证券资本市场团队"),

    /**
     * 知识产权保护团队
     */
    IP_PROTECTION(11, "知识产权保护团队"),

    /**
     * 知识产权许可团队
     */
    IP_LICENSING(12, "知识产权许可团队"),

    /**
     * 专利团队
     */
    PATENT(13, "专利团队"),

    /**
     * 商标团队
     */
    TRADEMARK(14, "商标团队"),

    /**
     * 合规咨询团队
     */
    COMPLIANCE_CONSULTING(15, "合规咨询团队"),

    /**
     * 反垄断合规团队
     */
    ANTITRUST_COMPLIANCE(16, "反垄断合规团队"),

    /**
     * 数据合规团队
     */
    DATA_COMPLIANCE(17, "数据合规团队"),

    /**
     * 劳动法律团队
     */
    LABOR_LAW(18, "劳动法律团队"),

    /**
     * 房地产建设团队
     */
    REAL_ESTATE_CONSTRUCTION(19, "房地产建设团队"),

    /**
     * 金融法律团队
     */
    FINANCIAL_LAW(20, "金融法律团队"),

    /**
     * 破产团队
     */
    BANKRUPTCY(21, "破产团队"),

    /**
     * 海事海商团队
     */
    MARITIME_LAW(22, "海事海商团队"),

    /**
     * 国际贸易团队
     */
    INTERNATIONAL_TRADE(23, "国际贸易团队"),

    /**
     * 税务法律团队
     */
    TAX_LAW(24, "税务法律团队"),

    /**
     * 项目组
     */
    PROJECT_TEAM(25, "项目组");

    private final Integer value;
    private final String description;

    TeamTypeEnum(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public static TeamTypeEnum valueOf(Integer value) {
        for (TeamTypeEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        return null;
    }
} 