package com.lawfirm.model.contract.enums;

import lombok.Getter;

/**
 * 合同类型枚举
 * 根据律师事务所业务类型进行细分
 */
@Getter
public enum ContractTypeEnum {
    
    // 诉讼类合同
    CIVIL_LITIGATION(1, "民事诉讼代理合同", "MS"),
    CRIMINAL_LITIGATION(2, "刑事诉讼代理合同", "XS"),
    ADMINISTRATIVE_LITIGATION(3, "行政诉讼代理合同", "XZ"),
    
    // 非诉讼类合同
    NON_LITIGATION(10, "非诉讼法律服务合同", "FS"),
    ARBITRATION(11, "仲裁代理合同", "ZC"),
    MEDIATION(12, "调解代理合同", "TJ"),
    
    // 法律顾问类合同
    ANNUAL_LEGAL_ADVISOR(20, "常年法律顾问合同", "CG"),
    SPECIAL_LEGAL_ADVISOR(21, "专项法律顾问合同", "ZG"),
    
    // 知识产权类合同
    TRADEMARK_AGENCY(30, "商标代理合同", "SB"),
    PATENT_AGENCY(31, "专利代理合同", "ZL"),
    COPYRIGHT_AGENCY(32, "著作权代理合同", "ZZ"),
    IP_PROTECTION(33, "知识产权保护合同", "BH"),
    
    // 企业法律服务类合同
    DUE_DILIGENCE(40, "尽职调查合同", "JZ"),
    COMPLIANCE_REVIEW(41, "合规审查合同", "HG"),
    MERGER_ACQUISITION(42, "企业并购重组合同", "BG"),
    CORPORATE_RESTRUCTURE(43, "公司重组法律服务合同", "CZ"),
    
    // 文件服务类合同
    LEGAL_DOCUMENT_REVIEW(50, "法律文件审查合同", "WJ"),
    LEGAL_OPINION(51, "法律意见书合同", "YJ"),
    CONTRACT_DRAFTING(52, "合同起草服务合同", "QC"),
    
    // 特殊领域服务合同
    REAL_ESTATE_LEGAL(60, "房地产法律服务合同", "FD"),
    LABOR_DISPUTE(61, "劳动争议代理合同", "LD"),
    DEBT_COLLECTION(62, "债务清收服务合同", "ZW"),
    MARITIME_LEGAL(63, "海事海商法律服务合同", "HS"),
    FINANCE_LEGAL(64, "金融法律服务合同", "JR"),
    
    // 培训咨询类合同
    LEGAL_TRAINING(70, "法律培训服务合同", "PX"),
    LEGAL_CONSULTATION(71, "法律咨询服务合同", "ZX"),
    LEGAL_RESEARCH(72, "法律研究服务合同", "YJ"),
    
    // 其他
    OTHER(99, "其他法律服务合同", "QT");

    private final int code;
    private final String description;
    private final String prefix;

    ContractTypeEnum(int code, String description, String prefix) {
        this.code = code;
        this.description = description;
        this.prefix = prefix;
    }
    
    /**
     * 根据编码获取枚举
     * 
     * @param code 编码
     * @return 合同类型枚举
     */
    public static ContractTypeEnum getByCode(int code) {
        for (ContractTypeEnum type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        return OTHER;
    }
    
    /**
     * 获取所有合同类型的可选列表
     * 用于前端下拉框展示
     * 
     * @return 合同类型列表
     */
    public static ContractTypeEnum[] getAllTypes() {
        return values();
    }
} 