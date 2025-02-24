package com.lawfirm.model.cases.enums.civilcause;

import com.lawfirm.model.base.enums.BaseEnum;
import lombok.Getter;

/**
 * 与企业有关的纠纷案由
 */
public class EnterpriseCause {

    /**
     * 与企业有关的纠纷一级案由
     */
    @Getter
    public enum First implements BaseEnum<String> {
        
        ENTERPRISE("与企业有关的纠纷", "20");

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
     * 与企业有关的纠纷二级案由
     */
    @Getter
    public enum Second implements BaseEnum<String> {
        
        // 248.企业出资人权益确认纠纷
        INVESTOR_RIGHTS_CONFIRMATION("企业出资人权益确认纠纷", "1248"),
        
        // 249.侵害企业出资人权益纠纷
        INVESTOR_RIGHTS_INFRINGEMENT("侵害企业出资人权益纠纷", "1249"),
        
        // 250.企业公司制改造合同纠纷
        COMPANY_REFORM("企业公司制改造合同纠纷", "1250"),
        
        // 251.企业股份合作制改造合同纠纷
        SHARE_COOPERATIVE_REFORM("企业股份合作制改造合同纠纷", "1251"),
        
        // 252.企业债权转股权合同纠纷
        DEBT_TO_EQUITY("企业债权转股权合同纠纷", "1252"),
        
        // 253.企业分立合同纠纷
        ENTERPRISE_DIVISION("企业分立合同纠纷", "1253"),
        
        // 254.企业租赁经营合同纠纷
        ENTERPRISE_LEASE("企业租赁经营合同纠纷", "1254"),
        
        // 255.企业出售合同纠纷
        ENTERPRISE_SALE("企业出售合同纠纷", "1255"),
        
        // 256.挂靠经营合同纠纷
        AFFILIATED_OPERATION("挂靠经营合同纠纷", "1256"),
        
        // 257.企业兼并合同纠纷
        ENTERPRISE_MERGER("企业兼并合同纠纷", "1257"),
        
        // 258.联营合同纠纷
        JOINT_OPERATION("联营合同纠纷", "1258"),
        
        // 259.企业承包经营合同纠纷
        ENTERPRISE_CONTRACT("企业承包经营合同纠纷", "1259"),
        
        // 260.中外合资经营企业合同纠纷
        JOINT_VENTURE("中外合资经营企业合同纠纷", "1260"),
        
        // 261.中外合作经营企业合同纠纷
        COOPERATIVE_ENTERPRISE("中外合作经营企业合同纠纷", "1261");

        private final String description;
        private final String code;
        private final First parentCause = First.ENTERPRISE;

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
     * 企业承包经营合同纠纷三级案由
     */
    @Getter
    public enum EnterpriseContract implements BaseEnum<String> {
        
        // (1)中外合资经营企业承包经营合同纠纷
        JOINT_VENTURE_CONTRACT("中外合资经营企业承包经营合同纠纷", "125901"),
        
        // (2)中外合作经营企业承包经营合同纠纷
        COOPERATIVE_ENTERPRISE_CONTRACT("中外合作经营企业承包经营合同纠纷", "125902"),
        
        // (3)外商独资企业承包经营合同纠纷
        FOREIGN_ENTERPRISE_CONTRACT("外商独资企业承包经营合同纠纷", "125903"),
        
        // (4)乡镇企业承包经营合同纠纷
        TOWNSHIP_ENTERPRISE_CONTRACT("乡镇企业承包经营合同纠纷", "125904");

        private final String description;
        private final String code;
        private final Second parentCause = Second.ENTERPRISE_CONTRACT;

        EnterpriseContract(String description, String code) {
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