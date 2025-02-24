package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 人格权纠纷案由
 */
public class PersonalityRightCause {

    /**
     * 一、人格权纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        PERSONALITY_RIGHT("人格权纠纷", "01");

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
     * 人格权二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 1.生命权、身体权、健康权纠纷
        LIFE_BODY_HEALTH("生命权、身体权、健康权纠纷", "0101"),
        
        // 2.姓名权纠纷
        NAME_RIGHT("姓名权纠纷", "0102"),
        
        // 3.名称权纠纷
        TITLE_RIGHT("名称权纠纷", "0103"),
        
        // 4.肖像权纠纷
        PORTRAIT_RIGHT("肖像权纠纷", "0104"),
        
        // 5.声音保护纠纷
        VOICE_PROTECTION("声音保护纠纷", "0105"),
        
        // 6.名誉权纠纷
        REPUTATION_RIGHT("名誉权纠纷", "0106"),
        
        // 7.荣誉权纠纷
        HONOR_RIGHT("荣誉权纠纷", "0107"),
        
        // 8.隐私权、个人信息保护纠纷
        PRIVACY_INFO_PROTECTION("隐私权、个人信息保护纠纷", "0108"),
        
        // 9.婚姻自主权纠纷
        MARRIAGE_AUTONOMY("婚姻自主权纠纷", "0109"),
        
        // 10.人身自由权纠纷
        PERSONAL_FREEDOM("人身自由权纠纷", "0110"),
        
        // 11.一般人格权纠纷
        GENERAL_PERSONALITY("一般人格权纠纷", "0111");

        private final String description;
        private final String code;
        private final First parentCause = First.PERSONALITY_RIGHT;

        Second(String description, String code) {
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
     * 隐私权、个人信息保护纠纷三级案由
     */
    @Getter
    public enum PrivacyInfoProtection implements BaseEnum<String> {
        
        // (1)隐私权纠纷
        PRIVACY_RIGHT("隐私权纠纷", "010801"),
        
        // (2)个人信息保护纠纷
        PERSONAL_INFO_PROTECTION("个人信息保护纠纷", "010802");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PRIVACY_INFO_PROTECTION;

        PrivacyInfoProtection(String description, String code) {
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
     * 一般人格权纠纷三级案由
     */
    @Getter
    public enum GeneralPersonality implements BaseEnum<String> {
        
        // (1)平等就业权纠纷
        EQUAL_EMPLOYMENT("平等就业权纠纷", "011101");

        private final String description;
        private final String code;
        private final Second parentCause = Second.GENERAL_PERSONALITY;

        GeneralPersonality(String description, String code) {
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
}