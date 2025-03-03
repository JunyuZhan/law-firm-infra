package com.lawfirm.model.finance.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 收入类型枚举
 */
@Getter
public enum IncomeTypeEnum implements BaseEnum<String> {
    
    CASE_INCOME("CASE_INCOME", "案件收入"),
    CONSULTATION_INCOME("CONSULTATION_INCOME", "咨询收入"),
    RETAINER_FEE("RETAINER_FEE", "顾问费"),
    DOCUMENT_REVIEW("DOCUMENT_REVIEW", "文件审查费"),
    CONTRACT_DRAFTING("CONTRACT_DRAFTING", "合同起草费"),
    LEGAL_OPINION("LEGAL_OPINION", "法律意见书费"),
    COURT_APPEARANCE("COURT_APPEARANCE", "出庭费"),
    MEDIATION_FEE("MEDIATION_FEE", "调解费"),
    ARBITRATION_FEE("ARBITRATION_FEE", "仲裁费"),
    TRAINING_INCOME("TRAINING_INCOME", "培训收入"),
    INTEREST_INCOME("INTEREST_INCOME", "利息收入"),
    OTHER_INCOME("OTHER_INCOME", "其他收入");

    private final String value;
    private final String description;

    IncomeTypeEnum(String value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 