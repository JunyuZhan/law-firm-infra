package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 保险纠纷案由
 */
public class InsuranceCause {

    /**
     * 保险纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        INSURANCE("保险纠纷", "27");

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
     * 保险纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 333.财产保险合同纠纷
        PROPERTY_INSURANCE("财产保险合同纠纷", "1333"),
        
        // 334.人身保险合同纠纷
        PERSONAL_INSURANCE("人身保险合同纠纷", "1334"),
        
        // 335.再保险合同纠纷
        REINSURANCE("再保险合同纠纷", "1335"),
        
        // 336.保险经纪合同纠纷
        INSURANCE_BROKERAGE("保险经纪合同纠纷", "1336"),
        
        // 337.保险代理合同纠纷
        INSURANCE_AGENCY("保险代理合同纠纷", "1337"),
        
        // 338.进出口信用保险合同纠纷
        IMPORT_EXPORT_INSURANCE("进出口信用保险合同纠纷", "1338"),
        
        // 339.保险费纠纷
        INSURANCE_PREMIUM("保险费纠纷", "1339");

        private final String description;
        private final String code;
        private final First parentCause = First.INSURANCE;

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
     * 财产保险合同纠纷三级案由
     */
    @Getter
    public enum PropertyInsurance implements BaseEnum<String> {
        
        // (1)财产损失保险合同纠纷
        PROPERTY_LOSS("财产损失保险合同纠纷", "133301"),
        
        // (2)责任保险合同纠纷
        LIABILITY("责任保险合同纠纷", "133302"),
        
        // (3)信用保险合同纠纷
        CREDIT("信用保险合同纠纷", "133303"),
        
        // (4)保证保险合同纠纷
        GUARANTEE("保证保险合同纠纷", "133304"),
        
        // (5)保险人代位求偿权纠纷
        SUBROGATION("保险人代位求偿权纠纷", "133305");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PROPERTY_INSURANCE;

        PropertyInsurance(String description, String code) {
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
     * 人身保险合同纠纷三级案由
     */
    @Getter
    public enum PersonalInsurance implements BaseEnum<String> {
        
        // (1)人寿保险合同纠纷
        LIFE("人寿保险合同纠纷", "133401"),
        
        // (2)意外伤害保险合同纠纷
        ACCIDENT("意外伤害保险合同纠纷", "133402"),
        
        // (3)健康保险合同纠纷
        HEALTH("健康保险合同纠纷", "133403");

        private final String description;
        private final String code;
        private final Second parentCause = Second.PERSONAL_INSURANCE;

        PersonalInsurance(String description, String code) {
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