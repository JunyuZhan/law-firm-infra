package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 劳动人事争议案由
 */
public class LaborPersonnelCause {

    /**
     * 劳动争议一级案由
     */
    @Getter
    public enum LaborFirst implements BaseEnum<String> {
        
        LABOR_DISPUTE("劳动争议", "17");

        private final String description;
        private final String code;

        LaborFirst(String description, String code) {
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
     * 劳动争议二级案由
     */
    @Getter
    public enum LaborSecond implements BaseEnum<String> {
        
        // 186.劳动合同纠纷
        LABOR_CONTRACT("劳动合同纠纷", "1186"),
        
        // 187.社会保险纠纷
        SOCIAL_INSURANCE("社会保险纠纷", "1187"),
        
        // 188.福利待遇纠纷
        WELFARE("福利待遇纠纷", "1188");

        private final String description;
        private final String code;
        private final LaborFirst parentCause = LaborFirst.LABOR_DISPUTE;

        LaborSecond(String description, String code) {
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
     * 劳动合同纠纷三级案由
     */
    @Getter
    public enum LaborContract implements BaseEnum<String> {
        
        // (1)确认劳动关系纠纷
        CONFIRM_LABOR_RELATION("确认劳动关系纠纷", "118601"),
        
        // (2)集体合同纠纷
        COLLECTIVE_CONTRACT("集体合同纠纷", "118602"),
        
        // (3)劳务派遣合同纠纷
        LABOR_DISPATCH("劳务派遣合同纠纷", "118603"),
        
        // (4)非全日制用工纠纷
        PART_TIME("非全日制用工纠纷", "118604"),
        
        // (5)追索劳动报酬纠纷
        LABOR_REMUNERATION("追索劳动报酬纠纷", "118605"),
        
        // (6)经济补偿金纠纷
        ECONOMIC_COMPENSATION("经济补偿金纠纷", "118606"),
        
        // (7)竞业限制纠纷
        NON_COMPETE("竞业限制纠纷", "118607");

        private final String description;
        private final String code;
        private final LaborSecond parentCause = LaborSecond.LABOR_CONTRACT;

        LaborContract(String description, String code) {
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
     * 社会保险纠纷三级案由
     */
    @Getter
    public enum SocialInsurance implements BaseEnum<String> {
        
        // (1)养老保险待遇纠纷
        PENSION("养老保险待遇纠纷", "118701"),
        
        // (2)工伤保险待遇纠纷
        WORK_INJURY("工伤保险待遇纠纷", "118702"),
        
        // (3)医疗保险待遇纠纷
        MEDICAL("医疗保险待遇纠纷", "118703"),
        
        // (4)生育保险待遇纠纷
        MATERNITY("生育保险待遇纠纷", "118704"),
        
        // (5)失业保险待遇纠纷
        UNEMPLOYMENT("失业保险待遇纠纷", "118705");

        private final String description;
        private final String code;
        private final LaborSecond parentCause = LaborSecond.SOCIAL_INSURANCE;

        SocialInsurance(String description, String code) {
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
     * 人事争议一级案由
     */
    @Getter
    public enum PersonnelFirst implements BaseEnum<String> {
        
        PERSONNEL_DISPUTE("人事争议", "18");

        private final String description;
        private final String code;

        PersonnelFirst(String description, String code) {
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
     * 人事争议二级案由
     */
    @Getter
    public enum PersonnelSecond implements BaseEnum<String> {
        
        // 189.聘用合同纠纷
        EMPLOYMENT_CONTRACT("聘用合同纠纷", "1189"),
        
        // 190.聘任合同纠纷
        APPOINTMENT_CONTRACT("聘任合同纠纷", "1190"),
        
        // 191.辞职纠纷
        RESIGNATION("辞职纠纷", "1191"),
        
        // 192.辞退纠纷
        DISMISSAL("辞退纠纷", "1192");

        private final String description;
        private final String code;
        private final PersonnelFirst parentCause = PersonnelFirst.PERSONNEL_DISPUTE;

        PersonnelSecond(String description, String code) {
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