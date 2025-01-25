package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 劳动争议、人事争议案由
 */
@Getter
public enum LaborPersonnelCauseEnum implements BaseEnum<String> {
    
    // 劳动争议
    LABOR_CONTRACT("劳动合同纠纷"),
    SOCIAL_INSURANCE("社会保险纠纷"),
    WELFARE("福利待遇纠纷"),
    
    // 人事争议
    EMPLOYMENT_CONTRACT("聘用合同纠纷"),
    APPOINTMENT_CONTRACT("聘任合同纠纷"),
    RESIGNATION("辞职纠纷"),
    DISMISSAL("辞退纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.LABOR_PERSONNEL;

    LaborPersonnelCauseEnum(String description) {
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