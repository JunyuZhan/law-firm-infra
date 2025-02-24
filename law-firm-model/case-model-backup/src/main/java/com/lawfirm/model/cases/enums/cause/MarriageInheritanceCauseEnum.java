package com.lawfirm.model.cases.enums.cause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 婚姻家庭、继承纠纷案由
 */
@Getter
public enum MarriageInheritanceCauseEnum implements BaseEnum<String> {
    
    // 婚姻家庭纠纷
    MARRIAGE_PROPERTY("婚约财产纠纷"),
    MARRIAGE_DIVISION("婚内夫妻财产分割纠纷"),
    DIVORCE("离婚纠纷"),
    DIVORCE_PROPERTY("离婚后财产纠纷"),
    DIVORCE_DAMAGES("离婚后损害责任纠纷"),
    MARRIAGE_INVALID("婚姻无效纠纷"),
    MARRIAGE_REVOKE("撤销婚姻纠纷"),
    MARRIAGE_AGREEMENT("夫妻财产约定纠纷"),
    COHABITATION("同居关系纠纷"),
    PARENT_CHILD("亲子关系纠纷"),
    SUPPORT_CHILD("抚养纠纷"),
    SUPPORT_SPOUSE("扶养纠纷"),
    SUPPORT_PARENT("赡养纠纷"),
    ADOPTION("收养关系纠纷"),
    GUARDIANSHIP("监护权纠纷"),
    VISITATION("探望权纠纷"),
    FAMILY_PROPERTY("分家析产纠纷"),
    
    // 继承纠纷
    LEGAL_INHERITANCE("法定继承纠纷"),
    WILL_INHERITANCE("遗嘱继承纠纷"),
    INHERITANCE_DEBT("被继承人债务清偿纠纷"),
    BEQUEST("遗赠纠纷"),
    BEQUEST_SUPPORT("遗赠扶养协议纠纷"),
    ESTATE_MANAGEMENT("遗产管理纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.MARRIAGE_INHERITANCE;

    MarriageInheritanceCauseEnum(String description) {
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