package com.lawfirm.model.cases.enums;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 人格权纠纷案由
 */
@Getter
public enum PersonalityRightCauseEnum implements BaseEnum<String> {
    
    LIFE_BODY_HEALTH("生命权、身体权、健康权纠纷"),
    NAME_RIGHT("姓名权纠纷"),
    TITLE_RIGHT("名称权纠纷"),
    PORTRAIT_RIGHT("肖像权纠纷"),
    VOICE_PROTECTION("声音保护纠纷"),
    REPUTATION_RIGHT("名誉权纠纷"),
    HONOR_RIGHT("荣誉权纠纷"),
    PRIVACY_PERSONAL_INFO("隐私权、个人信息保护纠纷"),
    MARRIAGE_FREEDOM("婚姻自主权纠纷"),
    PERSONAL_FREEDOM("人身自由权纠纷"),
    GENERAL_PERSONALITY("一般人格权纠纷");

    private final String description;
    private final CaseCauseEnum parentCause = CaseCauseEnum.PERSONALITY_RIGHT;

    PersonalityRightCauseEnum(String description) {
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