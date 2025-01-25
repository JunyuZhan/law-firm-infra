package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 案由枚举
 */
@Getter
public enum CaseCauseEnum implements BaseEnum<String> {
    
    PERSONALITY_RIGHT("人格权纠纷"),
    MARRIAGE_INHERITANCE("婚姻家庭、继承纠纷"),
    PROPERTY_RIGHT("物权纠纷"),
    CONTRACT("合同、准合同纠纷"),
    IP_COMPETITION("知识产权与竞争纠纷"),
    LABOR_PERSONNEL("劳动争议、人事争议"),
    MARITIME("海事海商纠纷"),
    COMPANY_SECURITIES("与公司、证券、保险、票据等有关的民事纠纷"),
    TORT("侵权责任纠纷"),
    NON_LITIGATION("非讼程序案件案由"),
    SPECIAL_LITIGATION("特殊诉讼程序案件案由");

    private final String description;

    CaseCauseEnum(String description) {
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }
} 